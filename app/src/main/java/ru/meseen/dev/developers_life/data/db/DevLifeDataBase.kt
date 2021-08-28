package ru.meseen.dev.developers_life.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.meseen.dev.developers_life.data.db.dao.FeedDao
import ru.meseen.dev.developers_life.data.db.dao.FeedPageKeyDao
import ru.meseen.dev.developers_life.data.db.entity.FeedEntity
import ru.meseen.dev.developers_life.data.db.entity.PageKeyEntity

@Database(
    entities = [PageKeyEntity::class, FeedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DevLifeDataBase
    : RoomDatabase() {

    abstract fun feedPageKeyDao(): FeedPageKeyDao
    abstract fun feedDao(): FeedDao

    companion object {
        const val PAGE_TABLE_NAME = "PAGE_TABLE_NAME"
        const val DATA_TABLE_NAME = "DATA_TABLE_NAME"
    }
}