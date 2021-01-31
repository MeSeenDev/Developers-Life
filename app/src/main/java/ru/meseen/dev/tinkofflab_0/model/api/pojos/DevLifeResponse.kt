package ru.meseen.dev.tinkofflab_0.model.api.pojos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DevLifeResponse(

	@SerialName("result")
	val result: List<ResultItem>,

	@SerialName("totalCount")
	val totalCount: Int? = null
)
@Serializable
data class ResultItem(

	@SerialName("date")
	val date: String? = null,

	@SerialName("previewURL")
	val previewURL: String? = null,

	@SerialName("author")
	val author: String? = null,

	@SerialName("description")
	val description: String? = null,

	@SerialName("type")
	val type: String? = null,

	@SerialName("videoSize")
	val videoSize: Int? = null,

	@SerialName("gifURL")
	val gifURL: String? = null,

	@SerialName("videoPath")
	val videoPath: String? = null,

	@SerialName("videoURL")
	val videoURL: String? = null,

	@SerialName("fileSize")
	val fileSize: Int? = null,

	@SerialName("gifSize")
	val gifSize: Int? = null,

	@SerialName("commentsCount")
	val commentsCount: Int? = null,

	@SerialName("width")
	val width: String? = null,

	@SerialName("votes")
	val votes: Int? = null,

	@SerialName("id")
	val id: Int,

	@SerialName("height")
	val height: String? = null,

	@SerialName("canVote")
	val canVote: Boolean? = null
)
