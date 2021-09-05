package ru.meseen.dev.developers_life.ui.main

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.data.api.SectionType
import ru.meseen.dev.developers_life.databinding.MainFragmentBinding
import ru.meseen.dev.developers_life.ui.base.BaseFragment
import ru.meseen.dev.developers_life.ui.main.adapter.FooterLoadStateAdapter
import ru.meseen.dev.developers_life.ui.main.adapter.HeaderLoadStateAdapter
import ru.meseen.dev.developers_life.ui.main.adapter.PageDevListAdapter
import ru.meseen.dev.developers_life.ui.main.viewmodels.MainViewModel

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class MainFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val adapter = PageDevListAdapter()
    private val viewModel: MainViewModel by viewModels()

    private var _vb: MainFragmentBinding? = null

    private val vb: MainFragmentBinding by lazy { _vb!! }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = MainFragmentBinding.inflate(LayoutInflater.from(requireContext()))
        return vb.root
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(bottomNavigationListener)

        adapterConfig()

        lifecycleScope.launchWhenCreated {
            viewModel.networkStatus.observe(viewLifecycleOwner, { isConnected ->
                if (isConnected) {
                    hideBanner()
                } else {
                    showBanner("Lost Internet connection")
                }
            })
        }
        vb.viewPager2.registerOnPageChangeCallback(pageListener)
        vb.nextButton.setOnClickListener(nextListener)
        vb.prevButton.setOnClickListener(previousListener)

    }

    private fun showBanner(message: String) {
        vb.errorContainer.isVisible = true
        vb.errorTextView.text = message
    }

    private fun hideBanner() {
        vb.errorContainer.isVisible = false
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
                    vb.nextButton.isVisible = !isError
                    if (isError) {
                        showBanner((refreshState as LoadState.Error).error.message ?: "Empty List")
                    } else {
                        hideBanner()
                    }
                }
            }
        }
    }

    private val bottomNavigationListener =
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.latest -> {
                    viewModel.showPosts(SectionType.LATEST)
                    true
                }
                R.id.top -> {
                    viewModel.showPosts(SectionType.TOP)
                    true
                }
                R.id.hot -> {
                    viewModel.showPosts(SectionType.HOT)
                    true
                }
                else -> false
            }
        }

    private val pageListener = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
          hidePrev(position)
        }

        override fun onPageSelected(position: Int) {
            hidePrev(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            vb.swipeRefreshLayout.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
        }
    }

    private val nextListener = View.OnClickListener {
        changePosition(Step.NEXT)
    }

    private val previousListener = View.OnClickListener {
        changePosition(Step.PREVIOUS)
    }

    private fun changePosition(step: Step) {
        val curItem = vb.viewPager2.currentItem
        if (step == Step.NEXT) {
            vb.viewPager2.currentItem = curItem + 1
        } else {
            vb.viewPager2.currentItem = if ((curItem - 1) < 0) 0 else curItem - 1
        }
    }

    private fun hidePrev(curItem: Int) {
        if (curItem <= 0) {
            vb.prevButton.hide()
        } else {
            vb.prevButton.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb.viewPager2.unregisterOnPageChangeCallback(pageListener)
        _vb = null
    }

    enum class Step {
        NEXT, PREVIOUS
    }

}