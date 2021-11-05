package ru.meseen.dev.developers_life.mapper

import ru.meseen.dev.developers_life.data.api.pojos.FeedItem
import ru.meseen.dev.developers_life.data.db.entity.FavFeedEntity
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
            section = selectionType,
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
            section = feedEntity.section,
            favorite = feedEntity.favorite,
        )

    fun fromModelToEntity(feedModel: FeedModel): FeedEntity =
        FeedEntity(
            date = feedModel.date,
            previewURL = feedModel.previewURL,
            author = feedModel.author,
            description = feedModel.description,
            type = feedModel.type,
            videoSize = feedModel.videoSize,
            gifURL = feedModel.gifURL,
            videoPath = feedModel.videoPath,
            videoURL = feedModel.videoURL,
            fileSize = feedModel.fileSize,
            gifSize = feedModel.gifSize,
            commentsCount = feedModel.commentsCount,
            width = feedModel.width,
            votes = feedModel.votes,
            post_id = feedModel.post_id,
            height = feedModel.height,
            canVote = feedModel.canVote,
            section = feedModel.section,
            favorite = feedModel.favorite,
        )

    fun fromModelToFavEntity(feedModel: FeedModel): FavFeedEntity =
        FavFeedEntity(
            date = feedModel.date,
            previewURL = feedModel.previewURL,
            author = feedModel.author,
            description = feedModel.description,
            type = feedModel.type,
            videoSize = feedModel.videoSize,
            gifURL = feedModel.gifURL,
            videoPath = feedModel.videoPath,
            videoURL = feedModel.videoURL,
            fileSize = feedModel.fileSize,
            gifSize = feedModel.gifSize,
            commentsCount = feedModel.commentsCount,
            width = feedModel.width,
            votes = feedModel.votes,
            post_id = feedModel.post_id,
            height = feedModel.height,
            canVote = feedModel.canVote,
            section = feedModel.section,
            favorite = feedModel.favorite,
        )

    fun fromFavEntityToModel(feedModel: FavFeedEntity) =
        FeedModel(
            date = feedModel.date,
            previewURL = feedModel.previewURL,
            author = feedModel.author,
            description = feedModel.description,
            type = feedModel.type,
            videoSize = feedModel.videoSize,
            gifURL = feedModel.gifURL,
            videoPath = feedModel.videoPath,
            videoURL = feedModel.videoURL,
            fileSize = feedModel.fileSize,
            gifSize = feedModel.gifSize,
            commentsCount = feedModel.commentsCount,
            width = feedModel.width,
            votes = feedModel.votes,
            post_id = feedModel.post_id,
            height = feedModel.height,
            canVote = feedModel.canVote,
            section = feedModel.section,
            favorite = feedModel.favorite,
        )

    fun fromFavEntityToFeedEntity(feedModel: FavFeedEntity) =
        FeedEntity(
            date = feedModel.date,
            previewURL = feedModel.previewURL,
            author = feedModel.author,
            description = feedModel.description,
            type = feedModel.type,
            videoSize = feedModel.videoSize,
            gifURL = feedModel.gifURL,
            videoPath = feedModel.videoPath,
            videoURL = feedModel.videoURL,
            fileSize = feedModel.fileSize,
            gifSize = feedModel.gifSize,
            commentsCount = feedModel.commentsCount,
            width = feedModel.width,
            votes = feedModel.votes,
            post_id = feedModel.post_id,
            height = feedModel.height,
            canVote = feedModel.canVote,
            section = feedModel.section,
            favorite = false,
        )

    fun fromFeedEntityToFavEntity(feedModel: FeedEntity) =
        FavFeedEntity(
            date = feedModel.date,
            previewURL = feedModel.previewURL,
            author = feedModel.author,
            description = feedModel.description,
            type = feedModel.type,
            videoSize = feedModel.videoSize,
            gifURL = feedModel.gifURL,
            videoPath = feedModel.videoPath,
            videoURL = feedModel.videoURL,
            fileSize = feedModel.fileSize,
            gifSize = feedModel.gifSize,
            commentsCount = feedModel.commentsCount,
            width = feedModel.width,
            votes = feedModel.votes,
            post_id = feedModel.post_id,
            height = feedModel.height,
            canVote = feedModel.canVote,
            section = feedModel.section,
            favorite = true,
        )
}