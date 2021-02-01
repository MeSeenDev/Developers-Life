package ru.meseen.dev.tinkofflab_0.ui.main

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import ru.meseen.dev.tinkofflab_0.R
import ru.meseen.dev.tinkofflab_0.model.api.DevApi
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
    private lateinit var snack: Snackbar


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
        viewPager2 = view.findViewById(R.id.viewPager2)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        next = view.findViewById(R.id.nextButton)
        prev = view.findViewById(R.id.prevButton)
        initSnackbar(view)
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFields(view)
        viewPager2.registerOnPageChangeCallback(pageListener)

        val bottomNavigationView =
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener)

        val adapter = PageDevListAdapter(application = application)
        adapterConfig(adapter)



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
    private fun adapterConfig(adapter: PageDevListAdapter) {

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
                .collect { viewPager2.currentItem = 0 } ///Обновляшка и сбрасовашка
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
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.latest -> {
                    viewModel.showPosts(DevApi.SectionType.LATEST.selection)
                    true
                }
                R.id.top -> {
                    viewModel.showPosts(DevApi.SectionType.TOP.selection)
                    true
                }
                R.id.hot -> {
                    viewModel.showPosts(DevApi.SectionType.HOT.selection)
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