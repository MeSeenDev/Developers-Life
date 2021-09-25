package ru.meseen.dev.developers_life.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import ru.meseen.dev.developers_life.data.db.entity.FavFeedEntity

/**
 * @author Vyacheslav Doroshenko
 */
@Dao
interface FavFeedDao : BaseDao<FavFeedEntity>{

    @Query("DELETE FROM FAV_TABLE")
    fun dropTable()
}