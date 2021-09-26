package ru.meseen.dev.developers_life.ui.fragments.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.meseen.dev.developers_life.databinding.RandomFragmentBinding
import ru.meseen.dev.developers_life.databinding.TopFragmentBinding
import ru.meseen.dev.developers_life.ui.base.BaseFragment

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class TopFragment : BaseFragment() {

    private var _vb : TopFragmentBinding? = null
    private val vb : TopFragmentBinding by lazy { _vb!!}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = TopFragmentBinding.inflate(inflater)
        return vb.root
    }
}