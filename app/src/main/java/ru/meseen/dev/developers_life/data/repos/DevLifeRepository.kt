package ru.meseen.dev.developers_life.data.repos

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.model.FeedModel

interface DevLifeRepository {
    fun loadData(
        query: DevLiveQuery
    ): Flow<PagingData<FeedModel>>
}