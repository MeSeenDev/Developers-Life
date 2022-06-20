package ru.meseen.dev.developers_life.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.meseen.dev.developers_life.R
import ru.meseen.dev.developers_life.databinding.MainActivityBinding
import ru.meseen.dev.developers_life.extensions.getResourceDrawable
import ru.meseen.dev.developers_life.ui.base.BaseActivity

/**
 * @author Doroshenko Vyacheslav
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding: MainActivityBinding
            by lazy { MainActivityBinding.inflate(LayoutInflater.from(this)) }

    private val bottomAppBar by lazy { binding.bottomNavigationView }

    var isBottomNavigationViewVisible: Boolean
        get() = bottomAppBar.isVisible
        set(value) {
            bottomAppBar.isVisible = value
        }

    val titleText by lazy { binding.mainTitle }

    var isNavigateBackVisible: Boolean = false
        set(value) {
            field = value
            binding.mainToolbar.navigationIcon = if (value) {
                getResourceDrawable(R.drawable.ic_arrow_prev)
            } else {
                null
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(binding.root)
            bottomAppBar.setupWithNavController(navController)

            mainToolbar.setNavigationOnClickListener (onNavBackListener)
            mainToolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.favorite -> {
                        navController.navigate(R.id.favoritesFragment)
                        true
                    }
                    else -> false
                }
            }
        }

    }

    var onNavBackListener: View.OnClickListener = View.OnClickListener { view ->
        navController.navigateUp()
    }
    set(value) {
        field =value
        binding?.mainToolbar?.setNavigationOnClickListener(value)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.favorite -> {

                Toast.makeText(this, "Not Implemented", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }

}