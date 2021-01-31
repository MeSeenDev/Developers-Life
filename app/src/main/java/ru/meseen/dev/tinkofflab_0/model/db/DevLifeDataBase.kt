package ru.meseen.dev.tinkofflab_0.model.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import ru.meseen.dev.tinkofflab_0.model.db.dao.PageKeyDao
import ru.meseen.dev.tinkofflab_0.model.db.dao.ResultsDao
import ru.meseen.dev.tinkofflab_0.model.db.entity.PageKeyEntity
import ru.meseen.dev.tinkofflab_0.model.db.entity.ResultEntity

@Database(
    entities = [PageKeyEntity::class, ResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DevLifeDataBase
    : RoomDatabase() {

    abstract fun pageKeyDao(): PageKeyDao
    abstract fun resultsDao(): ResultsDao

    companion object {
        private const val DATA_BASE_NAME = "DEV_LIFE.db"
        const val PAGE_TABLE_NAME = "PAGE_TABLE_NAME"
        const val DATA_TABLE_NAME = "DATA_TABLE_NAME"

        @Volatile
        private var INSTANCE: DevLifeDataBase? = null

        fun getInstance(application: Application, scope: CoroutineScope): DevLifeDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext, DevLifeDataBase::class.java,
                    DATA_BASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}