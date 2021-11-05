package ru.meseen.dev.developers_life.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.meseen.dev.developers_life.data.api.DevLifeService
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase
import ru.meseen.dev.developers_life.data.db.entity.FeedEntity
import ru.meseen.dev.developers_life.data.db.entity.PageKeyEntity
import ru.meseen.dev.developers_life.exceptions.EmptyFeedException
import ru.meseen.dev.developers_life.mapper.FeedMapper
import ru.meseen.dev.developers_life.ui.base.BaseRemoteMediator

/**
 * @author Doroshenko Vyacheslav
 */
@ExperimentalPagingApi
class DevLifeRemoteMediator(
    private val query: DevLiveQuery,
    private val service: DevLifeService,
    private val dataBase: DevLifeDataBase,
    private var mapper: FeedMapper
) : BaseRemoteMediator<Int, FeedEntity, PageKeyEntity>() {
    private val pageKeyDao = dataBase.feedPageKeyDao()
    private val resultsDao = dataBase.feedDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedEntity>
    ): MediatorResult = withContext(Dispatchers.IO) {
        try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    getRemoteKeyClosestToCurrentPosition(state) { pageKeyDao.remoteKeyById(it) }?.let { remoteKeys ->
                        remoteKeys.nextPage?.minus(1)
                    } ?: query.startPage
                }
                LoadType.PREPEND -> {
                    getRemoteKeyForFirstItem(state) { pageKeyDao.remoteKeyById(it) }?.let { remoteKeys ->
                        remoteKeys.prevPage
                            ?: return@withContext MediatorResult.Success(endOfPaginationReached = true)
                    }
                        ?: return@withContext MediatorResult.Success(endOfPaginationReached = false)

                }
                LoadType.APPEND -> {
                    getRemoteKeyForLastItem(state) {
                        pageKeyDao.remoteKeyById(it)
                    }?.let { remoteKeys ->
                        remoteKeys.nextPage
                            ?: return@withContext MediatorResult.Success(endOfPaginationReached = true)
                    }
                        ?: return@withContext MediatorResult.Success(endOfPaginationReached = false)
                }
            }


            val resultsItem = if (query.feedSection == "random") // FIXME: 25.10.2021 refactor
                listOf(service.loadRandom())
            else
                service.loadData(section = query.feedSection, page = page).feed

            if (resultsItem.isEmpty()) throw EmptyFeedException("Feed is Empty")

            val resultEntitys = resultsItem.map { feedItem ->
                mapper.fromResponseToEntity(feedItem, query.feedSection).apply {
                    favorite = dataBase.feedFavorite().isExists(feedItem.id.toLong())
                }
            }


            val endOfPaginationReached = resultEntitys.isEmpty()

            dataBase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    pageKeyDao.deleteByListType(query.feedSection)
                    resultsDao.deleteByListType(query.feedSection)
                }
                val prevPage = if (page == query.startPage) null else page.minus(1)
                val nextPage =
                    if (endOfPaginationReached) null else page.plus(1)

                val keys = resultEntitys.map {
                    PageKeyEntity(
                        _id = it._id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                        section = query.feedSection
                    )
                }

                resultsDao.insert(resultEntitys)
                pageKeyDao.insert(keys)
            }
            return@withContext MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Throwable) {
            return@withContext MediatorResult.Error(e)
        }
    }

}