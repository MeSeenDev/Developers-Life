package ru.meseen.dev.developers_life.data.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Doroshenko Vyacheslav
 */
@Serializable
data class BaseResponse<T>
    (
    @SerialName("result")
    val feed: List<T> = listOf(),

    @SerialName("totalCount")
    val totalCount: Int = 0
)