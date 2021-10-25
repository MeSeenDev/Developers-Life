package ru.meseen.dev.developers_life.data.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Doroshenko Vyacheslav
 */
@Serializable
data class FeedItem(

    @SerialName("date")
    val date: String = "",

    @SerialName("previewURL")
    val previewURL: String = "",

    @SerialName("author")
    val author: String = "",

    @SerialName("description")
    val description: String = "",

    @SerialName("type")
    val type: String = "",

    @SerialName("videoSize")
    val videoSize: Int = 0,

    @SerialName("gifURL")
    val gifURL: String = "",

    @SerialName("videoPath")
    val videoPath: String = "",

    @SerialName("videoURL")
    val videoURL: String = "",

    @SerialName("fileSize")
    val fileSize: Int = 0,

    @SerialName("gifSize")
    val gifSize: Int = 0,

    @SerialName("commentsCount")
    val commentsCount: Int = 0,

    @SerialName("width")
    val width: String = "",

    @SerialName("votes")
    val votes: Int = 0,

    @SerialName("id")
    val id: Int,

    @SerialName("height")
    val height: String = "",

    @SerialName("canVote")
    val canVote: Boolean = false
)
