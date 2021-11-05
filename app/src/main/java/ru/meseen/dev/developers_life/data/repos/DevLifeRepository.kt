package ru.meseen.dev.developers_life.data.repos

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.data.db.entity.FavFeedEntity
import ru.meseen.dev.developers_life.model.FeedModel

/**
 * @author Doroshenko Vyacheslav
 */
interface DevLifeRepository : FavDevLifeRepository {
    fun loadData(
        query: DevLiveQuery
    ): Flow<PagingData<FeedModel>>

    suspend fun emitFavItem(entity: FavFeedEntity)

}