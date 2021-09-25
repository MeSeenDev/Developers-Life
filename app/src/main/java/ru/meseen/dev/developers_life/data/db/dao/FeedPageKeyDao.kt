package ru.meseen.dev.developers_life.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.meseen.dev.developers_life.data.db.entity.PageKeyEntity
import ru.meseen.dev.developers_life.ui.base.BaseEntity

/**
 * @author Doroshenko Vyacheslav
 */
@Dao
interface FeedPageKeyDao : BaseDao<PageKeyEntity>{

    @Query("SELECT * FROM PAGE_TABLE WHERE _id = :id")
    suspend fun remoteKeyById(id: Long): PageKeyEntity

    @Query("DELETE FROM PAGE_TABLE WHERE section = :section")
    suspend fun deleteByListType(section: String)

}