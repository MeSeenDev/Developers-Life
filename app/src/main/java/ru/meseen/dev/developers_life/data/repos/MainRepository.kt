package ru.meseen.dev.developers_life.data.repos

import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.meseen.dev.developers_life.data.api.DevLifeService
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase
import ru.meseen.dev.developers_life.data.db.entity.FavFeedEntity
import ru.meseen.dev.developers_life.mapper.FeedMapper
import ru.meseen.dev.developers_life.model.FeedModel
import javax.inject.Inject

/**
 * @author Doroshenko Vyacheslav
 */
class MainRepository @Inject constructor(
    private val dataBase: DevLifeDataBase,
    private val service: DevLifeService,
    private val mapper: FeedMapper,
) : DevLifeRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun loadData(
        query: DevLiveQuery,
    ) = Pager(
        config = PagingConfig(pageSize = query.pageSize, initialLoadSize = query.pageSize),
        remoteMediator = DevLifeRemoteMediator(
            query = query, service = service,
            dataBase = dataBase, mapper = mapper
        )
    ) {
        dataBase.feedDao().pagingSource(query.feedSection)
    }.flow.map { data -> data.map { entity -> mapper.fromEntityToModel(entity) }}
        .flowOn(Dispatchers.IO)

    override suspend fun emitFavItem(entity: FavFeedEntity) {
        dataBase.apply {
            feedFavorite().updateFav(entity)
            feedDao().update(mapper.fromFavEntityToFeedEntity(entity))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadFavData(): Flow<PagingData<FeedModel>> =
        Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { dataBase.feedFavorite().loadFav() }
        ).flow.map { data -> data.map { entity -> mapper.fromFavEntityToModel(entity) } }
            .flowOn(Dispatchers.IO)


}