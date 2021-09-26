package ru.meseen.dev.developers_life.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import ru.meseen.dev.developers_life.R

/**
 * @author Vyacheslav Doroshenko
 */
open class BaseActivity : AppCompatActivity() {


    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    val navController by lazy { navHostFragment.navController }

    val appBarConfiguration by lazy {
        AppBarConfiguration(
            navGraph = navController.graph,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

    }
}