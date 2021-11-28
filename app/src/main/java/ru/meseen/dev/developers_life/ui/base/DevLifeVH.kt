package ru.meseen.dev.developers_life.ui.base

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.databinding.MainItemBinding
import ru.meseen.dev.developers_life.extensions.dp
import ru.meseen.dev.developers_life.extensions.getResourceDrawable
import ru.meseen.dev.developers_life.model.FeedModel

/**
 * @author Doroshenko Vyacheslav
 */
class DevLifeVH(
    private val binding: MainItemBinding,
    val onFavClick: (model: FeedModel) -> Unit
) : BaseHolder<FeedModel>(binding.root) {

    private val context by lazy { binding.root.context }

    override fun bind(model: FeedModel) {

        (binding.postImageView.layoutParams as ConstraintLayout.LayoutParams).let { params ->
            params.height = model.height.dp
            binding.postImageView.layoutParams = params
        }

        binding.postImageView.setOnClickListener { Toast.makeText(context, "height${model.height.dp}", Toast.LENGTH_SHORT).show() }

    Glide.with(itemView.context).asGif()
            .load(model.gifURL)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.ic_place_holder)
            .listener(progressBarListener)
            .error(R.drawable.ic_round_error_outline_24)
            .into(binding.postImageView)

        binding.descriptionTextView.text = model.description
        binding.authorTextView.text = model.author
        binding.dateTextView.text = model.date

        binding.share.setOnClickListener {
            Intent().apply {
                action = ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, model.description)
                putExtra(Intent.EXTRA_TEXT, model.gifURL)
                context.startActivity(Intent.createChooser(this, model.gifURL))
            }
        }
        updateFavStat(model)
        binding.addFav.setOnClickListener {
            model.favorite = !model.favorite
            updateFavStat(model)
            onFavClick(model)
        }
    }

    private fun updateFavStat(model: FeedModel) {
        binding.addFav.setImageDrawable(
            context.getResourceDrawable(getFavId(model))
        )
    }

    @DrawableRes
    private fun getFavId(model: FeedModel) =
        if (model.favorite)
            R.drawable.ic_baseline_favorite_on
        else
            R.drawable.ic_baseline_favorite_off

    private val progressBarListener = object : RequestListener<GifDrawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<GifDrawable>?,
            isFirstResource: Boolean
        ): Boolean {
            binding.progressBar.isVisible = false
            return false
        }

        override fun onResourceReady(
            resource: GifDrawable?,
            model: Any?,
            target: Target<GifDrawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            binding.progressBar.isVisible = false
            return false
        }
    }

}