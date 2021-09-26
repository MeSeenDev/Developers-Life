package ru.meseen.dev.developers_life.ui.fragments.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.meseen.dev.developers_life.databinding.RandomFragmentBinding
import ru.meseen.dev.developers_life.ui.base.BaseFragment
import java.util.*

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class RandomFragment : BaseFragment() {

    private var _vb : RandomFragmentBinding? = null
    private val vb : RandomFragmentBinding by lazy { _vb!!}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = RandomFragmentBinding.inflate(inflater)
        return vb.root
    }
}