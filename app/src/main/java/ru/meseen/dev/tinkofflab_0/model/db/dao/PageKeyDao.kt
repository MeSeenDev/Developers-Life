package ru.meseen.dev.tinkofflab_0.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meseen.dev.tinkofflab_0.model.db.entity.PageKeyEntity


@Dao
interface PageKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: PageKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<PageKeyEntity>)

    @Query("SELECT * FROM PAGE_TABLE_NAME WHERE section = :section")
    suspend fun remoteKeyByPost(section: String): PageKeyEntity

    @Query("SELECT * FROM PAGE_TABLE_NAME WHERE _id = :id")
    suspend fun remoteKeyById(id: Long): PageKeyEntity?

    @Query("DELETE FROM PAGE_TABLE_NAME WHERE section = :section")
    suspend fun deleteByListType(section: String)

    @Query("DELETE FROM PAGE_TABLE_NAME")
    suspend fun removeAll()

}