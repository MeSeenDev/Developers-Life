package ru.meseen.dev.developers_life.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.data.api.SectionType
import ru.meseen.dev.developers_life.ui.base.BaseFragment
import ru.meseen.dev.developers_life.ui.main.adapter.FooterLoadStateAdapter
import ru.meseen.dev.developers_life.ui.main.adapter.HeaderLoadStateAdapter
import ru.meseen.dev.developers_life.ui.main.adapter.PageDevListAdapter
import ru.meseen.dev.developers_life.ui.main.viewmodels.MainViewModel

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var next: FloatingActionButton
    private lateinit var prev: FloatingActionButton
    private lateinit var snack: Snackbar
    private lateinit var errorContainer: FrameLayout
    private lateinit var errorText: MaterialTextView

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    private fun initFields(view: View) {
        viewPager2 = view.findViewById(R.id.viewPager2)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        next = view.findViewById(R.id.nextButton)
        prev = view.findViewById(R.id.prevButton)
        errorContainer = view.findViewById(R.id.errorContainer)
        errorText = view.findViewById(R.id.errorTextView)
        initSnackbar(view)
    }

    private val adapter = PageDevListAdapter()

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFields(view)
        viewPager2.registerOnPageChangeCallback(pageListener)

        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(bottomNavigationListener)


        adapterConfig()



        lifecycleScope.launchWhenCreated {
            viewModel.networkStatus.observe(viewLifecycleOwner, {
                Log.d("TAG", "viewModel.networkStatus: $it")
                showSnackBar(it)
            })
        }

        next.setOnClickListener(nextListener)
        prev.setOnClickListener(previousListener)


    }


    private fun initSnackbar(view: View) {
        snack = Snackbar.make(
            view.findViewById(R.id.mainParent),
            "No Internet Connection",
            Snackbar.LENGTH_INDEFINITE
        )
        snack.anchorView = view.findViewById(R.id.nextButton)
        val params = snack.view.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.TOP
        snack.view.layoutParams = params

    }

    private fun showSnackBar(
        it: Boolean
    ) {
        if (!it) {
            snack.show()
        } else {
            if (snack.isShown)
                snack.dismiss()
        }
    }


    @ExperimentalPagingApi
    private fun adapterConfig() {

        viewPager2.adapter = adapter.withLoadStateHeaderAndFooter(
            header = HeaderLoadStateAdapter(adapter),
            footer = FooterLoadStateAdapter(adapter)
        )
        swipeRefreshLayout.setOnRefreshListener { adapter.refresh() }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
                (loadStates.refresh is LoadState.Error).let { isError ->
                    errorContainer.isVisible = isError
                    next.isVisible = !isError
                    if (isError) {
                        errorText.text =
                            (loadStates.refresh as LoadState.Error).error.message ?: "Empty List"
                    }
                }

            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
                errorContainer.isVisible = adapter.loadStateFlow.toList().isEmpty()

            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.map { it.refresh }
                .distinctUntilChanged()
                .collect {
                    if (it is LoadState.NotLoading) {
                        // PagingDataAdapter.itemCount here
                        Log.d("TAG", "onViewCreated: ${adapter.itemCount}")
                    }
                }
        }

    }

    private val bottomNavigationListener =
        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.latest -> {
                    lifecycleScope.launch {
                        adapter.submitData(PagingData.empty())
                    }
                    viewModel.showPosts(SectionType.LATEST)
                    true
                }
                R.id.top -> {
                    lifecycleScope.launch {
                        adapter.submitData(PagingData.empty())
                    }
                    viewModel.showPosts(SectionType.TOP)
                    true
                }
                R.id.hot -> {
                    lifecycleScope.launch {
                        adapter.submitData(PagingData.empty())
                    }
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