package ru.meseen.dev.tinkofflab_0.model.db.entity

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.meseen.dev.tinkofflab_0.model.api.pojos.ResultItem
import ru.meseen.dev.tinkofflab_0.model.db.DevLifeDataBase.Companion.DATA_TABLE_NAME


@Entity(tableName = DATA_TABLE_NAME)
data class ResultEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    val _id: Long? = null,

    @ColumnInfo(name = "date")
    val date: String? = null,

    @ColumnInfo(name = "previewURL")
    val previewURL: String? = null,

    @ColumnInfo(name = "author")
    val author: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "videoSize")
    val videoSize: Int? = null,

    @ColumnInfo(name = "gifURL")
    val gifURL: String? = null,

    @ColumnInfo(name = "videoPath")
    val videoPath: String? = null,

    @ColumnInfo(name = "videoURL")
    val videoURL: String? = null,

    @ColumnInfo(name = "fileSize")
    val fileSize: Int? = null,

    @ColumnInfo(name = "gifSize")
    val gifSize: Int? = null,

    @ColumnInfo(name = "commentsCount")
    val commentsCount: Int? = null,

    @ColumnInfo(name = "width")
    val width: String? = null,

    @ColumnInfo(name = "votes")
    val votes: Int? = null,

    @ColumnInfo(name = "id")
    val post_id: Long,

    @ColumnInfo(name = "height")
    val height: String? = null,

    @ColumnInfo(name = "canVote")
    val canVote: Boolean? = null,

    @ColumnInfo(name = "section")
    val section: String

) {

    constructor(resultItem: ResultItem, selectionType: String) : this(
        _id = null,
        date = resultItem.date,
        previewURL = resultItem.previewURL,
        author = resultItem.author,
        description = resultItem.description,
        type = resultItem.type,
        videoSize = resultItem.videoSize,
        gifURL = resultItem.gifURL,
        videoPath = resultItem.videoPath,
        videoURL = resultItem.videoURL,
        fileSize = resultItem.fileSize,
        gifSize = resultItem.gifSize,
        commentsCount = resultItem.commentsCount,
        width = resultItem.width,
        votes = resultItem.votes,
        post_id = resultItem.id.toLong(),
        height = resultItem.height,
        canVote = resultItem.canVote,
        section = selectionType
    )


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResultEntity

        if (post_id != other.post_id) return false

        return true
    }

    override fun hashCode(): Int {
        return post_id.hashCode()
    }
}