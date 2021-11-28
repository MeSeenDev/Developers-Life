package ru.meseen.dev.developers_life.ui.fragments.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.databinding.RandomFragmentBinding
import ru.meseen.dev.developers_life.ui.base.BaseFragment
import ru.meseen.dev.developers_life.ui.base.BasePageChangeCallback
import ru.meseen.dev.developers_life.ui.base.adapters.FooterLoadStateAdapter
import ru.meseen.dev.developers_life.ui.base.adapters.HeaderLoadStateAdapter
import ru.meseen.dev.developers_life.ui.fragments.random.adapter.RandomPageAdapter

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class RandomFragment : BaseFragment() {

    private var _vb: RandomFragmentBinding? = null
    private val vb: RandomFragmentBinding by lazy { _vb!! }

    private val viewModel by viewModels<RandomViewModel>()
    private val adapter by lazy { RandomPageAdapter(viewModel::invertFav) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = RandomFragmentBinding.inflate(inflater)
        title = getString(R.string.developers_life)
        return vb.root
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterConfig()
        lifecycleScope.launchWhenCreated {
            viewModel.networkStatus.observe(viewLifecycleOwner, { isConnected ->

            })
        }
        vb.viewPager2.registerOnPageChangeCallback(pageListener)
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun adapterConfig() {
        vb.viewPager2.adapter = adapter.withLoadStateHeaderAndFooter(
            header = HeaderLoadStateAdapter(adapter),
            footer = FooterLoadStateAdapter(adapter)
        )
        vb.swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        stateObserve()
        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun stateObserve() {
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                val refreshState = loadStates.refresh

                vb.swipeRefreshLayout.isRefreshing = refreshState is LoadState.Loading
                (refreshState is LoadState.Error).let { isError ->
                    if (isError) {
                        showBanner((refreshState as LoadState.Error).error.message ?: "Empty List")
                    } else {
                        hideBanner()
                    }
                }
            }
        }
    }

    private val pageListener = object : BasePageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            vb.swipeRefreshLayout.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
        }
    }


    private fun showBanner(message: String) {
        vb.errorContainer.isVisible = true
        vb.errorTextView.text = message
    }

    private fun hideBanner() {
        vb.errorContainer.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb.viewPager2.unregisterOnPageChangeCallback(pageListener)
        _vb = null
    }


}