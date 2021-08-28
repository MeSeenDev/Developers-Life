package ru.meseen.dev.developers_life.data.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.flow.map
import ru.meseen.dev.developers_life.data.api.DevLifeService
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase
import ru.meseen.dev.developers_life.ui.main.mapper.FeedMapper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dataBase: DevLifeDataBase,
    private val service: DevLifeService,
    private val mapper: FeedMapper,
) : DevLifeRepository {

    @ExperimentalPagingApi
    override fun loadData(
        query: DevLiveQuery,
    ) = Pager(
        config = PagingConfig(pageSize = query.pageSize),
        remoteMediator = DevLifeRemoteMediator(
            query = query, service = service,
            dataBase = dataBase, mapper = mapper
        )
    ) {
        dataBase.feedDao().pagingSource(query.feedSection)
    }.flow.map { data -> data.map { entity -> mapper.fromEntityToModel(entity) } }

}