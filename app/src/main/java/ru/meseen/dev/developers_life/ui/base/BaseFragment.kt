package ru.meseen.dev.developers_life.ui.base

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

}