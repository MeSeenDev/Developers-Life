package ru.meseen.dev.developers_life.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.meseen.dev.developers_life.databinding.FavoriteFragmentBinding
import ru.meseen.dev.developers_life.ui.base.BaseFragment
import ru.meseen.dev.developers_life.ui.fragments.favorites.adapter.FavoriteAdapter

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class FavoritesFragment : BaseFragment() {

    private var _vb: FavoriteFragmentBinding? = null
    private val vb: FavoriteFragmentBinding by lazy { _vb!! }

    private val viewModel: FavoritesViewModel by viewModels()

    override val isNavigateBackVisible: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isBottomNavigationViewVisible = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = FavoriteFragmentBinding.inflate(inflater)
        title = "Favorites"
        return vb.root
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FavoriteAdapter(viewModel::deleteFromFav)
        vb.favRecycle.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest { list ->
                adapter.submitData(list)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}