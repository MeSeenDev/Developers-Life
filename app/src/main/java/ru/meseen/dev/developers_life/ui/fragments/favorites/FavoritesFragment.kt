package ru.meseen.dev.developers_life.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.meseen.dev.developers_life.databinding.FavoriteFragmentBinding
import ru.meseen.dev.developers_life.ui.base.BaseFragment

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class FavoritesFragment : BaseFragment() {

    private var _vb: FavoriteFragmentBinding? = null
    private val vb: FavoriteFragmentBinding by lazy { _vb!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = FavoriteFragmentBinding.inflate(inflater)
        return vb.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}