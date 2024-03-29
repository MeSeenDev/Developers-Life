package ru.meseen.dev.developers_life.ui.main.adapter.vh

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import ru.meseen.dev.developers_life.R

/**
 * @author Doroshenko Vyacheslav
 */
class LoadStateVH(itemView: View, private val retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    private val errorDescription: MaterialTextView = itemView.findViewById(R.id.errorTextView)

    @Suppress("unused")
    private val retryBtn: MaterialButton = itemView.findViewById<MaterialButton>(R.id.retryBtn)
        .also {
            it.setOnClickListener { retryCallback() }
        }


    fun bind(loadState: LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        errorDescription.isVisible =
            !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        errorDescription.text = (loadState as? LoadState.Error)?.error?.message


    }


}