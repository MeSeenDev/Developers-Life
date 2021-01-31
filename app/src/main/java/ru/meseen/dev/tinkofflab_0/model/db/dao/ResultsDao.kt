package ru.meseen.dev.tinkofflab_0.model.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meseen.dev.tinkofflab_0.model.db.entity.ResultEntity

@Dao
interface ResultsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<ResultEntity>)

    @Query("SELECT * FROM DATA_TABLE_NAME WHERE section LIKE :section")
    fun pagingSource(section: String): PagingSource<Int, ResultEntity>

    @Query("DELETE FROM DATA_TABLE_NAME")
    suspend fun clearAll()

    @Query("DELETE FROM DATA_TABLE_NAME WHERE section = :section")
    suspend fun deleteByListType(section: String)
}