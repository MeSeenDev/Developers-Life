package ru.meseen.dev.tinkofflab_0.model.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.tinkofflab_0.model.api.DevApi
import ru.meseen.dev.tinkofflab_0.model.api.query.Query
import ru.meseen.dev.tinkofflab_0.model.data.DevLifeRemoteMediator
import ru.meseen.dev.tinkofflab_0.model.db.DevLifeDataBase
import ru.meseen.dev.tinkofflab_0.model.db.entity.ResultEntity

class MainRepository(private val dataBase: DevLifeDataBase) : DevLifeRepository {


    @ExperimentalPagingApi
    override fun loadData(
        query: Query,
    ): Flow<PagingData<ResultEntity>> = Pager(
        config = PagingConfig(pageSize = query.getPageSize()),
        remoteMediator = DevLifeRemoteMediator(query, DevApi.service, dataBase)
    ) {
        dataBase.resultsDao().pagingSource(query.getSection())
    }.flow


}