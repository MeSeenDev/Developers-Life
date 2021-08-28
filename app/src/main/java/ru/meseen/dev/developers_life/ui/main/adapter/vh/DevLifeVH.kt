package ru.meseen.dev.developers_life.ui.main.adapter.vh

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.model.FeedModel


class DevLifeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val postImageView = itemView.findViewById<ImageView>(R.id.postImageView)
    private val descriptionTextView =
        itemView.findViewById<MaterialTextView>(R.id.descriptionTextView)
    private val authorTextView = itemView.findViewById<MaterialTextView>(R.id.authorTextView)
    private val dateTextView = itemView.findViewById<MaterialTextView>(R.id.dateTextView)


    fun bind(feedEntity: FeedModel?) {

        Log.d("TAG", "bind: ${feedEntity?.gifURL}")

        Glide.with(itemView.context).asGif()
            .load(feedEntity?.gifURL)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerCrop()
            .placeholder(R.drawable.ic_place_holder)
            .into(postImageView)
        descriptionTextView.text = feedEntity?.description ?: "Описание отсутствует"
        authorTextView.text = feedEntity?.author ?: "anonim"
        dateTextView.text = feedEntity?.date ?: "Jan 1, 1970 03:00:00 AM"

    }

}