package ru.meseen.dev.developers_life.ui.fragments.top

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
import ru.meseen.dev.developers_life.databinding.TopFragmentBinding
import ru.meseen.dev.developers_life.ui.base.BaseFragment
import ru.meseen.dev.developers_life.ui.base.BasePageChangeCallback
import ru.meseen.dev.developers_life.ui.fragments.latest.adapter.FooterLoadStateAdapter
import ru.meseen.dev.developers_life.ui.fragments.latest.adapter.HeaderLoadStateAdapter
import ru.meseen.dev.developers_life.ui.fragments.top.adapter.TopPageAdapter

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class TopFragment : BaseFragment() {

    private var _vb: TopFragmentBinding? = null
    private val vb: TopFragmentBinding by lazy { _vb!! }

    private val viewModel by viewModels<TopViewModel>()

    private val adapter = TopPageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = TopFragmentBinding.inflate(inflater)
        return vb.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterConfig()
        lifecycleScope.launchWhenCreated {
            viewModel.networkStatus.observe(viewLifecycleOwner, { isConnected ->

            })
        }
        vb.viewPager2.registerOnPageChangeCallback(pageListener)
    }

    @ExperimentalPagingApi
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

    private fun showBanner(message: String) {
        vb.errorContainer.isVisible = true
        vb.errorTextView.text = message
    }

    private fun hideBanner() {
        vb.errorContainer.isVisible = false
    }

    private val pageListener = object : BasePageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            vb.swipeRefreshLayout.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb.viewPager2.unregisterOnPageChangeCallback(pageListener)
        _vb = null
    }
}