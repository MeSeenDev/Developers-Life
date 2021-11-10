package ru.meseen.dev.developers_life.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.meseen.dev.developers_life.data.db.entity.FavFeedEntity

/**
 * @author Vyacheslav Doroshenko
 */
@Dao
interface FavFeedDao : BaseDao<FavFeedEntity> {

    @Query("SELECT * FROM FAV_TABLE")
    fun loadFav(): PagingSource<Int, FavFeedEntity>

    @Transaction
    fun updateFav(entity: FavFeedEntity) {
        if (isExists(entity.post_id)) {
            deleteById(entity.post_id)
        } else {
            insert(entity)
        }
    }

    @Query("SELECT COUNT(1) FROM FAV_TABLE WHERE id = :post_id")
    fun isExists(post_id: Long): Boolean

    @Query("DELETE FROM FAV_TABLE WHERE id LIKE :post_id")
    fun deleteById(post_id: Long)

    @Query("DELETE FROM FAV_TABLE")
    fun dropTable()
}