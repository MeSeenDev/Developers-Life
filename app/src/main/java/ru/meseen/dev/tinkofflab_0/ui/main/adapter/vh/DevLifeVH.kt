package ru.meseen.dev.tinkofflab_0.ui.main.adapter.vh

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.tinkofflab_0.R
import ru.meseen.dev.tinkofflab_0.model.db.entity.ResultEntity


class DevLifeVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val postImageView = itemView.findViewById<ImageView>(R.id.postImageView)
    private val descriptionTextView =
        itemView.findViewById<MaterialTextView>(R.id.descriptionTextView)
    private val authorTextView = itemView.findViewById<MaterialTextView>(R.id.authorTextView)
    private val dateTextView = itemView.findViewById<MaterialTextView>(R.id.dateTextView)


    fun bind(resultEntity: ResultEntity?, application: Application) {

        Log.d("TAG", "bind: ${resultEntity?.gifURL}")

        Glide.with(application).asGif()
            .load(resultEntity?.gifURL)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerCrop()
            .placeholder(R.drawable.ic_place_holder)
            .into(postImageView)
        descriptionTextView.text = resultEntity?.description ?: "Описание отсутствует"
        authorTextView.text = resultEntity?.author ?: "anonim"
        dateTextView.text = resultEntity?.date ?: "Jan 1, 1970 03:00:00 AM"

    }

}