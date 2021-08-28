package ru.meseen.dev.developers_life.data.api.query

data class DevLiveQuery(
    val feedSection: String,
    val startPage: Int = 0,
    val pageSize: Int = 5,
    val typesImages: String = "gif"
)
