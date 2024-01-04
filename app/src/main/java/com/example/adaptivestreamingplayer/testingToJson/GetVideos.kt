package com.example.adaptivestreamingplayer.testingToJson

import com.google.gson.annotations.SerializedName

data class GetVideos(
    @SerializedName("status"            ) var status            : Boolean?          = null,
    @SerializedName("message"           ) var message           : String?           = null,
    @SerializedName("videos"            ) var videos            : ArrayList<Videos> = arrayListOf(),
    @SerializedName("totalPages"        ) var totalPages        : Int?              = null,
    @SerializedName("totalRecords"      ) var totalRecords      : String?           = null,
    @SerializedName("publishedCount"    ) var publishedCount    : Int?              = null,
    @SerializedName("notPublishedCount" ) var notPublishedCount : Int?              = null
)
data class Videos (
    @SerializedName("videoId"       ) var videoId       : Int?              = null,
    @SerializedName("title"         ) var title         : String?           = null,
    @SerializedName("duration"      ) var duration      : String?           = null,
    @SerializedName("videoURL"      ) var videoURL      : String?           = null,
    @SerializedName("videoTypeId"   ) var videoTypeId   : String?           = null,
    @SerializedName("videoType"     ) var videoType     : String?           = null,
    @SerializedName("thumbnailURL"  ) var thumbnailURL  : String?           = null,
    @SerializedName("videoStatus"   ) var videoStatus   : String?           = null,
    @SerializedName("videoStatusId" ) var videoStatusId : Int?              = null,
    @SerializedName("nodeIds"       ) var nodeIds       : ArrayList<String> = arrayListOf(),
    @SerializedName("mediaTypeId"   ) var mediaTypeId   : Int?              = null,
    @SerializedName("language"      ) var language      : String?           = null,
    @SerializedName("teacher"       ) var teacher       : String?           = null,
    @SerializedName("description"   ) var description   : String?           = null,
    @SerializedName("fileSize"      ) var fileSize      : String?           = null
)