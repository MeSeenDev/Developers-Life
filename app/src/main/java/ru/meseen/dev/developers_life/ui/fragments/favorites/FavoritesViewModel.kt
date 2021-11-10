package ru.meseen.dev.developers_life.ui.fragments.favorites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.meseen.dev.developers_life.data.repos.FavDevLifeRepository
import ru.meseen.dev.developers_life.model.FeedModel
import javax.inject.Inject

/**
 * @author Vyacheslav Doroshenko
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: FavDevLifeRepository
) : ViewModel() {

    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = repository.loadFavData()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty()).cachedIn(viewModelScope)

    fun deleteFromFav(model: FeedModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFavorites(model)
        }
    }

}