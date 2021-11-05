package ru.meseen.dev.developers_life.data.db.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.meseen.dev.developers_life.data.db.DevLifeDataBase
import ru.meseen.dev.developers_life.ui.base.BaseEntity

/**
 * @author Vyacheslav Doroshenko
 */
@Entity(tableName = DevLifeDataBase.FAV_TABLE_NAME)
data class FavFeedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    override val _id: Long? = null,

    @ColumnInfo(name = "date")
    val date: String = "",

    @ColumnInfo(name = "previewURL")
    val previewURL: String = "",

    @ColumnInfo(name = "author")
    val author: String = "",

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "type")
    val type: String = "",

    @ColumnInfo(name = "videoSize")
    val videoSize: Int = 0,

    @ColumnInfo(name = "gifURL")
    val gifURL: String = "",

    @ColumnInfo(name = "videoPath")
    val videoPath: String = "",

    @ColumnInfo(name = "videoURL")
    val videoURL: String = "",

    @ColumnInfo(name = "fileSize")
    val fileSize: Int = 0,

    @ColumnInfo(name = "gifSize")
    val gifSize: Int = 0,

    @ColumnInfo(name = "commentsCount")
    val commentsCount: Int = 0,

    @ColumnInfo(name = "width")
    val width: String = "",

    @ColumnInfo(name = "votes")
    val votes: Int = 0,

    @ColumnInfo(name = "id", index = true)
    val post_id: Long,

    @ColumnInfo(name = "height")
    val height: String = "",

    @ColumnInfo(name = "canVote")
    val canVote: Boolean = false,

    @ColumnInfo(name = "section")
    val section: String = "",

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = true,
) : BaseEntity