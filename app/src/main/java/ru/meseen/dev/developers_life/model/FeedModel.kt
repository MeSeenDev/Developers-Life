package ru.meseen.dev.developers_life.model

/**
 * @author Vyacheslav Doroshenko
 */
data class FeedModel(
    val date: String = "",
    val previewURL: String = "",
    val author: String = "",
    val description: String = "",
    val type: String = "",
    val videoSize: Int = 0,
    val gifURL: String = "",
    val videoPath: String = "",
    val videoURL: String = "",
    val fileSize: Int = 0,
    val gifSize: Int = 0,
    val commentsCount: Int = 0,
    val width: String = "",
    val votes: Int = 0,
    val post_id: Long = 0,
    val height: String = "",
    val canVote: Boolean = false,
    val section: String = ""
)