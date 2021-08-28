package ru.meseen.dev.developers_life.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.model.FeedModel
import ru.meseen.dev.developers_life.ui.main.adapter.vh.DevLifeVH

class PageDevListAdapter :
    PagingDataAdapter<FeedModel, RecyclerView.ViewHolder>(DEV_LIFE_COMPARE) {


    companion object {
        private val DEV_LIFE_COMPARE = object : DiffUtil.ItemCallback<FeedModel>() {
            override fun areContentsTheSame(oldItem: FeedModel, newItem: FeedModel): Boolean =
                oldItem == newItem

            override fun getChangePayload(oldItem: FeedModel, newItem: FeedModel): Any = Any()

            override fun areItemsTheSame(oldItem: FeedModel, newItem: FeedModel): Boolean =
                oldItem.post_id == newItem.post_id && oldItem.section == oldItem.section

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return DevLifeVH(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DevLifeVH) {
            val item = getItem(position)
            holder.bind(item)
        }
    }


}