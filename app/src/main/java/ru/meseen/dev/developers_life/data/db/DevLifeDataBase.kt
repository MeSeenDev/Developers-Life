package ru.meseen.dev.developers_life.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.meseen.dev.developers_life.data.db.dao.FavFeedDao
import ru.meseen.dev.developers_life.data.db.dao.FeedDao
import ru.meseen.dev.developers_life.data.db.dao.FeedPageKeyDao
import ru.meseen.dev.developers_life.data.db.entity.FavFeedEntity
import ru.meseen.dev.developers_life.data.db.entity.FeedEntity
import ru.meseen.dev.developers_life.data.db.entity.PageKeyEntity

/**
 * @author Doroshenko Vyacheslav
 */
@Database(
    entities = [PageKeyEntity::class, FeedEntity::class, FavFeedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DevLifeDataBase
    : RoomDatabase() {

    abstract fun feedPageKeyDao(): FeedPageKeyDao
    abstract fun feedDao(): FeedDao
    abstract fun feedFavorite(): FavFeedDao

    companion object {
        const val PAGE_TABLE_NAME = "PAGE_TABLE"
        const val DATA_TABLE_NAME = "DATA_TABLE"
        const val FAV_TABLE_NAME = "FAV_TABLE"
    }
}