package ru.meseen.dev.tinkofflab_0.model.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.meseen.dev.tinkofflab_0.model.api.DevApi.START_PAGE
import ru.meseen.dev.tinkofflab_0.model.api.DevLifeService
import ru.meseen.dev.tinkofflab_0.model.api.query.Query
import ru.meseen.dev.tinkofflab_0.model.db.DevLifeDataBase
import ru.meseen.dev.tinkofflab_0.model.db.entity.PageKeyEntity
import ru.meseen.dev.tinkofflab_0.model.db.entity.ResultEntity
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class DevLifeRemoteMediator(
    private val query: Query,
    private val service: DevLifeService,
    private val dataBase: DevLifeDataBase
) : RemoteMediator<Int, ResultEntity>() {
    private val pageKeyDao = dataBase.pageKeyDao()
    private val resultsDao = dataBase.resultsDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: START_PAGE
                }
                LoadType.PREPEND -> {
                    // LoadType - PREPEND, значит некоторые данные были ранее загружены,
                    // чтобы мы могли получить удаленные ключи
                    // Если удаленные ключи равны null, значит, руки из жопы получаем недопустимое состояние от туда и Исключение
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                        ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                    val prevKey = remoteKeys.prevPage ?: return MediatorResult.Success(  // Если предыдущий ключ равен нулю, мы не можем запросить больше данных
                        endOfPaginationReached = true
                    )
                    remoteKeys.prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    if (remoteKeys?.nextPage == null) {
                        throw InvalidObjectException("Remote key should not be null for $loadType")
                    }
                    remoteKeys.nextPage
                }
            }

            val resultsItem = service.loadData(section = query.getSection(), page = page)
            val resultEntitys = resultsItem.result.map { ResultEntity(it, query.getSection()) }

            val endOfPaginationReached = resultEntitys.isEmpty()

            dataBase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    pageKeyDao.deleteByListType(query.getSection())
                    resultsDao.deleteByListType(query.getSection())
                }

                val prevPage = if (page == START_PAGE) null else page - 1
                val nextPage = if (endOfPaginationReached) null else page + 1
                val keys = resultEntitys.map {
                    PageKeyEntity(
                        _id = it.post_id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        section = query.getSection()
                    )
                }

                resultsDao.insertAll(resultEntitys)
                pageKeyDao.insertAll(keys = keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }

    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ResultEntity>): PageKeyEntity? {
        // Берем последнюю полученную страницу, содержащую элементы.
        // С этой последней страницы получаем последний элемент
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { resultEntity ->
                dataBase.pageKeyDao().remoteKeyById(resultEntity.post_id) // получение последнего удаленного ключа или null
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ResultEntity>): PageKeyEntity? {
        // Берем первую полученную страницу, содержащую элементы.
        // С этой первой страницы получаем первый элемент
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { resultEntity ->
                dataBase.pageKeyDao().remoteKeyById(resultEntity.post_id) // получение первого удаленного ключа или null
            }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ResultEntity>
    ): PageKeyEntity? {
        // Библиотека пагинации(3.0*) пытается загрузить данные после позиции привязки
        // Получаем элемент, ближайший к позиции привязки
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.post_id?.let { repoId ->
                dataBase.pageKeyDao().remoteKeyById(repoId)
            }
        }
    }


}