package ru.meseen.dev.tinkofflab_0.ui.main.viewmodels

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.meseen.dev.tinkofflab_0.App
import ru.meseen.dev.tinkofflab_0.model.api.ConnectionLiveData
import ru.meseen.dev.tinkofflab_0.model.api.query.DevLiveQuery

class MainViewModel(
    application: Application,
    private val handle: SavedStateHandle,
) : ViewModel() {

    private val repository = (application as App).latestRepository

    val networkStatus = ConnectionLiveData(application.applicationContext)

    companion object {
        const val KEY_POSTS = "KEY_POSTS"
        const val TYPE_QUERY = "latest"
    }

    init {
        if (!handle.contains(KEY_POSTS)) {
            handle.set(KEY_POSTS, TYPE_QUERY)
        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @ExperimentalPagingApi
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        handle.getLiveData<String>(KEY_POSTS)
            .asFlow()
            .flatMapLatest {
                repository.loadData(
                    DevLiveQuery(
                        it,
                        0,
                        5,
                        "gif"
                    )
                )
            }
            /*при изменении конфигурации (например, ротации) новая Активность (Фрагмент) получит существующие данные
            сразу!, а не будет загружать их с нуля.*/
            .cachedIn(viewModelScope)
    ).flattenMerge(2)


    private fun shouldShowPosts(
        sector: String
    ) = handle.get<String>(KEY_POSTS) != sector

    fun showPosts(section: String) {
        if (!shouldShowPosts(section)) return

        clearListCh.offer(Unit)

        handle.set(KEY_POSTS, section)
    }
}


class MyViewModelFabric(
    owner: SavedStateRegistryOwner,
    private val application: Application,
    args: Bundle? = null
) :
    AbstractSavedStateViewModelFactory(owner, args) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application, handle = handle) as T
        }

        throw  IllegalArgumentException("Не занаю такой вью модели")
    }

}