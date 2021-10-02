package ru.meseen.dev.developers_life.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import ru.meseen.dev.developers_life.data.api.DevLifeService
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase
import ru.meseen.dev.developers_life.data.db.entity.FeedEntity
import ru.meseen.dev.developers_life.data.db.entity.PageKeyEntity
import ru.meseen.dev.developers_life.exceptions.EmptyFeedException
import ru.meseen.dev.developers_life.ui.base.BaseRemoteMediator
import ru.meseen.dev.developers_life.mapper.FeedMapper

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
    ): MediatorResult {
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
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
                    }
                        ?: return MediatorResult.Success(endOfPaginationReached = false)

                }
                LoadType.APPEND -> {
                    getRemoteKeyForLastItem(state) {
                        pageKeyDao.remoteKeyById(it)
                    }?.let { remoteKeys ->
                        remoteKeys.nextPage
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
                    }
                        ?: return MediatorResult.Success(endOfPaginationReached = false)
                }
            }

            val resultsItem = service.loadData(section = query.feedSection, page = page)
            if (resultsItem.totalCount < 1) throw EmptyFeedException("Feed is Empty")
            val resultEntitys = resultsItem.feed.map {
                mapper.fromResponseToEntity(it, query.feedSection)
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
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Throwable) {
            return MediatorResult.Error(e)
        }
    }
}