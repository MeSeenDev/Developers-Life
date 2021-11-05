package ru.meseen.dev.developers_life.ui.fragments.favorites.adapter.vh

import android.content.Intent
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
import ru.meseen.dev.developers_life.databinding.MainFavItemBinding
import ru.meseen.dev.developers_life.model.FeedModel
import ru.meseen.dev.developers_life.ui.base.BaseHolder

/**
 * @author Vyacheslav Doroshenko
 */
class FavDevLifeVH(private val binding: MainFavItemBinding) : BaseHolder<FeedModel>(binding.root) {

    override fun bind(feedEntity: FeedModel) {
        Glide.with(itemView.context).asGif()
            .load(feedEntity.gifURL)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .placeholder(R.drawable.ic_place_holder)
            .listener(progressBarListener)
            .error(R.drawable.ic_round_error_outline_24)
            .into(binding.postImageView)

        binding.descriptionTextView.text = feedEntity.description ?: "Описание отсутствует"
        binding.authorTextView.text = feedEntity.author ?: "anonim"
        binding.dateTextView.text = feedEntity.date ?: "Jan 1, 1970 03:00:00 AM"

        binding.share.setOnClickListener {
            Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain";
                putExtra(Intent.EXTRA_SUBJECT, feedEntity.description);
                putExtra(Intent.EXTRA_TEXT, feedEntity.gifURL);
                binding.root.context.startActivity(Intent.createChooser(this, feedEntity.gifURL))
            }
        }
    }

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