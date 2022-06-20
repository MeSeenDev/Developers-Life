package ru.meseen.dev.developers_life.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.meseen.dev.developers_life.ui.activities.MainActivity

/**
 * @author Vyacheslav Doroshenko
 */
open class BaseFragment : Fragment() {

    private val mainActivity by lazy { (requireActivity() as MainActivity) }

    var isBottomNavigationViewVisible: Boolean
        get() = mainActivity.isBottomNavigationViewVisible
        set(value) {
            mainActivity.isBottomNavigationViewVisible = value
        }

    var title: String
        get() = mainActivity.titleText.text.toString()
        set(value) {
            mainActivity.titleText.text = value
        }

   /*
   * Надо ли отображать стрелку "назад" в тулбаре
   */
    open val isNavigateBackVisible: Boolean = false

    override fun onResume() {
        super.onResume()
        mainActivity.isBottomNavigationViewVisible = this@BaseFragment.isBottomNavigationViewVisible
        mainActivity.isNavigateBackVisible = isNavigateBackVisible
    }

    fun setOnNavBackListener(listener: View.OnClickListener){
        mainActivity.onNavBackListener = listener
    }

}