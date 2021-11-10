package ru.meseen.dev.developers_life.data.repos

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.meseen.dev.developers_life.model.FeedModel

/**
 * @author Vyacheslav Doroshenko
 */
interface FavDevLifeRepository {

    fun loadFavData(): Flow<PagingData<FeedModel>>

    suspend fun deleteFromFavorites(model: FeedModel)

}