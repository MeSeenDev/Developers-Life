package ru.meseen.dev.developers_life.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meseen.dev.developers_life.data.db.entity.FeedEntity

/**
 * @author Doroshenko Vyacheslav
 */
@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<FeedEntity>)

    @Query("SELECT * FROM DATA_TABLE_NAME WHERE section LIKE :section")
    fun pagingSource(section: String): PagingSource<Int, FeedEntity>

    @Query("DELETE FROM DATA_TABLE_NAME WHERE section = :section")
    suspend fun deleteByListType(section: String)
}