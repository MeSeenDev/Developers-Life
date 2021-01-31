package ru.meseen.dev.tinkofflab_0.ui.main.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.meseen.dev.tinkofflab_0.R
import ru.meseen.dev.tinkofflab_0.model.db.entity.ResultEntity
import ru.meseen.dev.tinkofflab_0.ui.main.adapter.vh.DevLifeVH

class PageDevListAdapter(private val application: Application) :
    PagingDataAdapter<ResultEntity, RecyclerView.ViewHolder>(DEV_LIFE_COMPARE) {


    companion object {
        private val DEV_LIFE_COMPARE = object : DiffUtil.ItemCallback<ResultEntity>() {
            override fun areItemsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultEntity, newItem: ResultEntity): Boolean {
                return oldItem.post_id == newItem.post_id
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return DevLifeVH(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DevLifeVH) {
            val item = getItem(position)
            holder.bind(item, application)
        }
    }


}