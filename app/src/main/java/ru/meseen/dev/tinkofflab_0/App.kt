package ru.meseen.dev.tinkofflab_0

import android.app.Application
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import ru.meseen.dev.tinkofflab_0.model.data.repos.DevLifeRepository
import ru.meseen.dev.tinkofflab_0.model.data.repos.LatestRepository
import ru.meseen.dev.tinkofflab_0.model.db.DevLifeDataBase

class App : Application() {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.wtf("ERROR", " ${coroutineContext.job.isCancelled} : ${throwable.localizedMessage}")
        }
    val globalScope = CoroutineScope(SupervisorJob() + coroutineExceptionHandler)
    val dataBase by lazy { DevLifeDataBase.getInstance(this, scope = globalScope) }

    val latestRepository: DevLifeRepository by lazy { LatestRepository(dataBase) }

}