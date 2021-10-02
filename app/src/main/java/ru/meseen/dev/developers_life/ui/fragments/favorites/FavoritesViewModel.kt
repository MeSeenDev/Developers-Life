package ru.meseen.dev.developers_life.ui.fragments.favorites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Vyacheslav Doroshenko
 */
@HiltViewModel
class FavoritesViewModel @Inject constructor(private val handle: SavedStateHandle) : ViewModel() {
}