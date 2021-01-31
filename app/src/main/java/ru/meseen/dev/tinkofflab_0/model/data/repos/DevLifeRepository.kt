package ru.meseen.dev.tinkofflab_0.model.data.repos

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.tinkofflab_0.model.api.DevLifeService
import ru.meseen.dev.tinkofflab_0.model.api.query.Query
import ru.meseen.dev.tinkofflab_0.model.db.DevLifeDataBase
import ru.meseen.dev.tinkofflab_0.model.db.entity.ResultEntity

interface DevLifeRepository {
    fun loadData(
        query: Query
    ): Flow<PagingData<ResultEntity>>
}