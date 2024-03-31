package com.example.adaptivestreamingplayer.urlIssue

import com.example.adaptivestreamingplayer.testingToJson.Video
import com.google.gson.annotations.SerializedName

data class VideoPlaylistResponse(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<VideoPlaylistData>? = null,
    var isVideoPlaylistLoading: Boolean = true
)

data class VideoPlaylistData(
    @SerializedName("playlistId") var playlistId: Int? = null,
    @SerializedName("playlistTitle") var playlistTitle: String? = null,
    @SerializedName("videosCount") var videosCount: Int? = null,
    @SerializedName("videos") var videos: ArrayList<Video>? = null
)

data class Subjects(
    @SerializedName("subjectId") var subjectId: Int? = null,
    @SerializedName("subject") var subject: String? = null
)
