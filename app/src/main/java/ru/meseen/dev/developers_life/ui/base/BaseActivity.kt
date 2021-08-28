package ru.meseen.dev.developers_life.ui.base

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.job

/**
 * @author Vyacheslav Doroshenko
 */
open class BaseActivity : AppCompatActivity() {
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.wtf("ERROR", " ${coroutineContext.job.isCancelled} : ${throwable.localizedMessage}")
        }

}