package ru.meseen.dev.developers_life.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.databinding.MainItemBinding
import ru.meseen.dev.developers_life.model.FeedModel
import ru.meseen.dev.developers_life.ui.main.adapter.vh.DevLifeVH

/**
 * @author Doroshenko Vyacheslav
 */
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
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DevLifeVH(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DevLifeVH) {
            holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycle_alpha)
            val item = getItem(position)
            holder.bind(item)
        }
    }


}