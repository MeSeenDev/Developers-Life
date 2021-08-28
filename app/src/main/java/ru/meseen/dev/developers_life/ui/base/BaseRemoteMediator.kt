package ru.meseen.dev.developers_life.ui.base

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

/**
 * @author Vyacheslav Doroshenko
 */
@ExperimentalPagingApi
abstract class BaseRemoteMediator<Key : Any, Value : BaseEntity, KeyEntity : BaseKeyEntity> :
    RemoteMediator<Key, Value>() {

    protected suspend inline fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Key, Value>,
        crossinline supplier: suspend (id: Long) -> KeyEntity
    ): KeyEntity? = state.anchorPosition?.let { position ->
        state.closestItemToPosition(position)?._id?.let { id ->
            supplier(id)
        }
    }

    protected suspend inline fun getRemoteKeyForFirstItem(
        state: PagingState<Key, Value>,
        crossinline supplier: suspend (id: Long) -> KeyEntity
    ): KeyEntity? = state.pages
        .firstOrNull { source -> source.data.isNotEmpty() }
        ?.data?.firstOrNull()?._id?.let { id ->
            supplier(id)
        }

    protected suspend inline fun getRemoteKeyForLastItem(
        state: PagingState<Key, Value>,
        crossinline supplier: suspend (id: Long) -> KeyEntity
    ): KeyEntity? = state.pages
        .lastOrNull { source -> source.data.isNotEmpty() }
        ?.data?.lastOrNull()?._id?.let { id ->
            supplier(id)
        }
}