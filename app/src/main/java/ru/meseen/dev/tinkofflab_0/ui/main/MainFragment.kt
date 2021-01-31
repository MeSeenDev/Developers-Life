package ru.meseen.dev.tinkofflab_0.ui.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import ru.meseen.dev.tinkofflab_0.R
import ru.meseen.dev.tinkofflab_0.ui.main.adapter.FooterLoadStateAdapter
import ru.meseen.dev.tinkofflab_0.ui.main.adapter.HeaderLoadStateAdapter
import ru.meseen.dev.tinkofflab_0.ui.main.adapter.PageDevListAdapter
import ru.meseen.dev.tinkofflab_0.ui.main.viewmodels.MainViewModel
import ru.meseen.dev.tinkofflab_0.ui.main.viewmodels.MyViewModelFabric

class MainFragment : Fragment() {

    companion object {
        fun newInstance(application: Application): MainFragment {
            val fragment = MainFragment()
            fragment.application = application
            return fragment
        }
    }

    lateinit var application: Application
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var next: FloatingActionButton
    private lateinit var prev: FloatingActionButton


    private val viewModel: MainViewModel by viewModels { MyViewModelFabric(this, application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    private fun initFields(view: View) {
        viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager2)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        next = view.findViewById(R.id.nextButton)
        prev = view.findViewById(R.id.prevButton)
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFields(view)
        val adapter = PageDevListAdapter(application = application)

        viewPager2.registerOnPageChangeCallback(pageListener)

        viewPager2.adapter = adapter.withLoadStateHeaderAndFooter(
            header = HeaderLoadStateAdapter(adapter),
            footer = FooterLoadStateAdapter(adapter)
        )
        swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { viewPager2.postInvalidate() }
        }

        next.setOnClickListener(nextListener)
        prev.setOnClickListener(previousListener)
    }


    private val pageListener = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            hidePrev(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            swipeRefreshLayout.isEnabled = state == ViewPager2.SCROLL_STATE_IDLE
        }
    }

    private val nextListener = View.OnClickListener {
        changePosition(Step.NEXT)
    }

    private val previousListener = View.OnClickListener {
        changePosition(Step.PREVIOUS)
    }

    private fun changePosition(step: Step) {
        val curItem = viewPager2.currentItem
        if (step == Step.NEXT) {
            viewPager2.currentItem = curItem + 1
        } else {

            viewPager2.currentItem = if ((curItem - 1) < 1) 0 else curItem - 1
        }
    }

    private fun hidePrev(curItem: Int) {
        if (curItem <= 0) {
            prev.hide()
        } else {
            prev.show()
        }

    }


    enum class Step {
        NEXT, PREVIOUS
    }
}