package ru.meseen.dev.developers_life.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Vyacheslav Doroshenko
 */
abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(model: T)
}