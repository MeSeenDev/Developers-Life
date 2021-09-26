package ru.meseen.dev.developers_life.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.meseen.dev.developers_life.databinding.MainActivityBinding
import ru.meseen.dev.developers_life.ui.base.BaseActivity

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
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}