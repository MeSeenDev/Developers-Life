package ru.meseen.dev.tinkofflab_0

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.meseen.dev.tinkofflab_0.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(application = application))
                .commitNow()
        }
    }

}