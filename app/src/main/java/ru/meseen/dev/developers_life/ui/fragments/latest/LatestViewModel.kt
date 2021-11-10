package ru.meseen.dev.developers_life.ui.fragments.latest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.meseen.dev.developers_life.data.api.ConnectionObserver
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.data.repos.DevLifeRepository
import ru.meseen.dev.developers_life.mapper.FeedMapper
import ru.meseen.dev.developers_life.model.FeedModel
import javax.inject.Inject

/**
 * @author Doroshenko Vyacheslav
 */
@HiltViewModel
class LatestViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: DevLifeRepository,
    val networkStatus: ConnectionObserver,
    private val mapper: FeedMapper,
) : ViewModel() {

    companion object {
        private const val KEY_POSTS = "KEY_POSTS"
        private const val TYPE_QUERY = "latest"
        private const val CLEAR_FIST = "CLEAR_FIST"
    }

    init {
        if (!handle.contains(KEY_POSTS)) {
            handle.set(KEY_POSTS, TYPE_QUERY)
        }
    }

    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = handle.getLiveData<String>(KEY_POSTS).asFlow()
        .flatMapLatest { typeQuery ->
            if (typeQuery == CLEAR_FIST) {
                flow { emit(PagingData.empty<FeedModel>()) }
            } else {
                repository.loadData(
                    DevLiveQuery(feedSection = typeQuery)
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty()).cachedIn(viewModelScope)


    fun invertFav(model: FeedModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.emitFavItem(mapper.fromModelToFavEntity(model))
        }
    }
}
