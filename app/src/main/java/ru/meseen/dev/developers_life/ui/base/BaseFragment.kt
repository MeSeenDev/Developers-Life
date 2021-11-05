package ru.meseen.dev.developers_life.ui.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.meseen.dev.developers_life.ui.activities.MainActivity

/**
 * @author Vyacheslav Doroshenko
 */
open class BaseFragment : Fragment() {


    val navController by lazy { (requireActivity() as MainActivity).navController }


    val bottomNavBar = (activity as MainActivity).appBarConfiguration
}