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
            post_id = resultItem.post_id.toLong(),
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
            width = try {
                feedEntity.width.toInt()
            } catch (exe: NumberFormatException) {
                400
            },
            votes = feedEntity.votes,
            post_id = feedEntity.post_id,
            height = try {
                feedEntity.height.toInt()
            } catch (exe: NumberFormatException) {
                400
            },
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
            width = feedModel.width.toString(),
            votes = feedModel.votes,
            post_id = feedModel.post_id,
            height = feedModel.height.toString(),
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
            width = feedModel.width.toString(),
            votes = feedModel.votes,
            post_id = feedModel.post_id,
            height = feedModel.height.toString(),
            canVote = feedModel.canVote,
            section = feedModel.section,
            favorite = feedModel.favorite,
        )

    fun fromFavEntityToModel(favFeedEntity: FavFeedEntity) =
        FeedModel(
            date = favFeedEntity.date,
            previewURL = favFeedEntity.previewURL,
            author = favFeedEntity.author,
            description = favFeedEntity.description,
            type = favFeedEntity.type,
            videoSize = favFeedEntity.videoSize,
            gifURL = favFeedEntity.gifURL,
            videoPath = favFeedEntity.videoPath,
            videoURL = favFeedEntity.videoURL,
            fileSize = favFeedEntity.fileSize,
            gifSize = favFeedEntity.gifSize,
            commentsCount = favFeedEntity.commentsCount,
            width = try {
                favFeedEntity.width.toInt()
            } catch (exe: NumberFormatException) {
                400
            },
            votes = favFeedEntity.votes,
            post_id = favFeedEntity.post_id,
            height = try {
                favFeedEntity.height.toInt()
            } catch (exe: NumberFormatException) {
                400
            },
            canVote = favFeedEntity.canVote,
            section = favFeedEntity.section,
            favorite = favFeedEntity.favorite,
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