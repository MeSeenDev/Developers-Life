package ru.meseen.dev.developers_life.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meseen.dev.developers_life.data.db.entity.PageKeyEntity


@Dao
interface FeedPageKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<PageKeyEntity>)

    @Query("SELECT * FROM PAGE_TABLE_NAME WHERE _id = :id")
    suspend fun remoteKeyById(id: Long): PageKeyEntity

    @Query("DELETE FROM PAGE_TABLE_NAME WHERE section = :section")
    suspend fun deleteByListType(section: String)

}