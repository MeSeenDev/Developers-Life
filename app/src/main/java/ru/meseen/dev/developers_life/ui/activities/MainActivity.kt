package ru.meseen.dev.developers_life.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.meseen.dev.developers_life.R
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
        binding.mainToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.favorite -> {
                    navController.navigate(R.id.favoritesFragment)
                    true
                }
                else -> false
            }
        }
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