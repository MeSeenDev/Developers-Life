package ru.meseen.dev.developers_life.ui.fragments.top

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.meseen.dev.developers_life.data.api.ConnectionObserver
import ru.meseen.dev.developers_life.data.api.SectionType
import ru.meseen.dev.developers_life.data.api.query.DevLiveQuery
import ru.meseen.dev.developers_life.data.repos.DevLifeRepository
import ru.meseen.dev.developers_life.model.FeedModel
import ru.meseen.dev.developers_life.ui.fragments.latest.LatestViewModel
import javax.inject.Inject

/**
 * @author Vyacheslav Doroshenko
 */
@HiltViewModel
class TopViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: DevLifeRepository,
    val networkStatus: ConnectionObserver
) : ViewModel() {

    companion object {
       private const val KEY_POSTS = "TOP_KEY_POSTS"
       private const val TYPE_QUERY = "top"
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
                repository.loadData(
                    DevLiveQuery(feedSection = typeQuery)
                )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}