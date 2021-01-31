package ru.meseen.dev.tinkofflab_0

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.meseen.dev.tinkofflab_0.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationListener)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(application = application))
                .commitNow()
        }
    }


    private val bottomNavigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.latest -> {

                    true
                }
                R.id.top -> {

                    true
                }
                R.id.hot -> {

                    true
                }
                else -> false
            }
        }


    enum class Tags {
        LATEST, TOP, HOT
    }


}