package com.example.adaptivestreamingplayer.testingToJson

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.adaptivestreamingplayer.utils.Constants
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
data class Video(
    var id: Int = 0,
    var chapterId: String? = null,
    var topicId: String? = null,
    var subtopicId: String? = null,
    @SerializedName("videoId") var videoId: Int = 0,
    @SerializedName("title") var title: String? = null, // Sending Data to a New Activity with Intent Extras
    var description: String? = null, // Description for media object #1var id: Int = 0, // 1
    @SerializedName("duration") var duration: String? = null, // 500var subjectId: Int = 0,
    @SerializedName("thumbnailURL") var thumbnailURL: String? = null, // https://s3.ca-central-1.amazonaws
    // .com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png
    var thumbnail: String? = null,
    var isCompleted: Boolean = false,
    var completeddate: Long = 0,
    var isBookmarked: Boolean = false,
    var bookmarkedDate: Long = 0,
    var watchedContentInSecs: Int = 0,
    var subjectId: String? = null,
    var userId: String? = "",
    var isPlaying: Boolean = true,
    var type: Int = 3,
    var playlistTypeId: Int = 0,
    var isMute: Boolean = false,
    var shortsView: Int = (1000..2000).random(), // Temporary Variable for Demo
    var playlistViews: Int = (500..1000).random(), // Temporary Variable for Demo
    @SerializedName("orderId") var orderId: Int = 0,
    @SerializedName("videoMappingId") var videoMappingId: Int = 0,
    // https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4
    @SerializedName("videoURL") var videoURL: String? = null,
    @SerializedName("videoTypeId") var videoTypeId: Int? = null,
    @SerializedName("videoType") var videoType: String? = null,
    @SerializedName("sourceName") var sourceName: String? = null,
    @SerializedName("subjects") var subjects: ArrayList<Subjects> = arrayListOf(),

    //extra added
    @SerializedName("videoSolutionId") var videoSolutionId:String?="",
    @SerializedName("chapterName")  var chapterName:String="",
    @SerializedName("topicName")  var topicName:String="",
    @SerializedName("videoName") var videoName:String="",
    @SerializedName("isLiked") var isLiked:Boolean?=false,
    @SerializedName("isDisLiked") var isDisLiked:Boolean?=false
) : Parcelable {


    val durationinsecs: Int
        get() {
            return try {
                duration = if (duration == "null" || duration == "" || duration == " " || duration == "\" \"") "0" else duration
                val num = duration?.replace(Regex("[^0-9]"), "")
                if (duration?.contains(Constants.MINUTES) == true)
                    (num?.toInt() ?: 0) * 1
                else if (duration?.contains(Constants.HOURS) == true)
                    (num?.toInt() ?: 0) * 60 * 60
                (num?.toInt() ?: 0)
            } catch (e: Exception) {
                try {
                    duration?.split(" ")?.first()?.toInt()?:0
                } catch (e: Exception) {
                    duration?.filter { it.isDigit() }?.toInt() ?: 0
                }
            }
        }


    val progressInPercentage: Int
        get() {
            return if (durationinsecs == 0) 0 else
                try {
                    watchedContentInSecs.times(100).div(durationinsecs)
                } catch (e: Exception) {
                    e.printStackTrace()
                    0
                }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        chapterId = parcel.readString(),
        topicId = parcel.readString(),
        subtopicId = parcel.readString(),
        videoId = parcel.readInt(),
        title = parcel.readString(),
        description = parcel.readString(),
        duration = parcel.readString(),
        thumbnailURL = parcel.readString(),
        thumbnail = parcel.readString(),
        videoURL = parcel.readString(),
        isCompleted = parcel.readByte() != 0.toByte(),
        completeddate = parcel.readLong(),
        isBookmarked=parcel.readByte() != 0.toByte(),
        bookmarkedDate = parcel.readLong(),
        watchedContentInSecs = parcel.readInt(),
        subjectId = parcel.readString(),
        userId = parcel.readString(),
        isPlaying = parcel.readByte() != 0.toByte(),
        type = parcel.readInt(),
        playlistTypeId = parcel.readInt(),
        isMute = parcel.readByte() != 0.toByte(),
        shortsView = parcel.readInt(),
        playlistViews = parcel.readInt(),
        orderId = parcel.readInt(),
        videoMappingId = parcel.readInt(),
        videoTypeId = parcel.readInt(),
        videoType = parcel.readString(),
        sourceName = parcel.readString(),
        isLiked = parcel.readInt() == 1,
        isDisLiked = parcel.readInt() == 1
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(chapterId)
        parcel.writeString(topicId)
        parcel.writeString(subtopicId)
        parcel.writeInt(videoId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(duration)
        parcel.writeString(thumbnailURL)
        parcel.writeString(thumbnail)
        parcel.writeString(videoURL)
        parcel.writeInt(if (isCompleted) 1 else 0) // Write boolean as an integer
        parcel.writeLong(completeddate)
        parcel.writeInt(if (isBookmarked) 1 else 0) // Write boolean as an integer
        parcel.writeLong(bookmarkedDate)
        parcel.writeInt(watchedContentInSecs)
        parcel.writeString(subjectId)
        parcel.writeString(userId)
        parcel.writeInt(if (isPlaying) 1 else 0) // Write boolean as an integer
        parcel.writeInt(type)
        parcel.writeInt(playlistTypeId)
        parcel.writeInt(if (isMute) 1 else 0) // Write boolean as an integer
        parcel.writeInt(shortsView)
        parcel.writeInt(playlistViews)
        parcel.writeInt(orderId)
        parcel.writeInt(videoMappingId)
        parcel.writeInt(videoTypeId?:0)
        parcel.writeString(videoType)
        parcel.writeString(sourceName)
        parcel.writeInt(if (isLiked == true) 1 else 0) // Write boolean as an integer
        parcel.writeInt(if (isDisLiked == true) 1 else 0) // Write boolean as an integer
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Video> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Video {
            return Video(parcel)
        }

        override fun newArray(size: Int): Array<Video?> {
            return arrayOfNulls(size)
        }
    }


}