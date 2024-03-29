package ru.meseen.dev.developers_life.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.ui.main.adapter.vh.LoadStateVH

/**
 * @author Doroshenko Vyacheslav
 */
class FooterLoadStateAdapter(private val adapter: PageDevListAdapter) :
    LoadStateAdapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.no_data, parent, false)
        return LoadStateVH(v) { adapter.retry() }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        if (holder is LoadStateVH) {
            holder.bind(loadState)
        }
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error
    }
}


