package ru.meseen.dev.developers_life.ui.fragments.latest.mapper

import ru.meseen.dev.developers_life.data.api.pojos.FeedItem
import ru.meseen.dev.developers_life.data.db.entity.FeedEntity
import ru.meseen.dev.developers_life.model.FeedModel
import javax.inject.Inject

/**
 * @author Vyacheslav Doroshenko
 */
class FeedMapper @Inject constructor() {

    fun fromResponseToEntity(resultItem: FeedItem, selectionType: String): FeedEntity =
        FeedEntity(
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

    fun fromEntityToModel(feedEntity: FeedEntity): FeedModel =
        FeedModel(
            date = feedEntity.date,
            previewURL = feedEntity.previewURL,
            author = feedEntity.author,
            description = feedEntity.description,
            type = feedEntity.type,
            videoSize = feedEntity.videoSize,
            gifURL = feedEntity.gifURL,
            videoPath = feedEntity.videoPath,
            videoURL = feedEntity.videoURL,
            fileSize = feedEntity.fileSize,
            gifSize = feedEntity.gifSize,
            commentsCount = feedEntity.commentsCount,
            width = feedEntity.width,
            votes = feedEntity.votes,
            post_id = feedEntity.post_id,
            height = feedEntity.height,
            canVote = feedEntity.canVote,
            section = feedEntity.section
        )
}