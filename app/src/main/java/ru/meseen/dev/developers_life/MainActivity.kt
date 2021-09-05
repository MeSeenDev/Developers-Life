package ru.meseen.dev.developers_life

import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import ru.meseen.dev.developers_life.databinding.MainActivityBinding
import ru.meseen.dev.developers_life.ui.base.BaseActivity
import ru.meseen.dev.developers_life.ui.main.MainFragment

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: MainActivityBinding
            by lazy { MainActivityBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}