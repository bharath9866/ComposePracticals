package com.example.adaptivestreamingplayer.memoryCard.model

import com.google.gson.annotations.SerializedName
data class MemoryFlashcardResponse(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: Data? = Data()
)

data class CODResponse(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<Flashcards> = arrayListOf()
)
data class BookMarkedMemoryCardResponse(
    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<Flashcards> = arrayListOf()
)
data class Data(
    @SerializedName("parent") var parent: Parent? = Parent(),
    @SerializedName("flashcards") var flashcards: ArrayList<Flashcards> = arrayListOf()
)

data class Flashcards(
    @SerializedName("flashcardId") var flashcardId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("titleMedia") var titleMedia: String? = null,
    @SerializedName("typeId") var typeId: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("statusId") var statusId: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("front") var front: Front? = Front(),
    @SerializedName("back") var back: Back? = Back(),
    @SerializedName("isBookmarked") var isBookmarked: Boolean? = false,
    @SerializedName("memoryCardId") var memoryCardId: Int? = null,
    @SerializedName("chapterId") var chapterId: Int? = null,
    @SerializedName("subjectId") var subjectId:Int?=null

)

data class Parent(
    @SerializedName("examId") var examId: Int? = null,
    @SerializedName("subjectId") var subjectId: Int? = null,
    @SerializedName("chapterId") var chapterId: Int? = null,
    @SerializedName("topicIds") var topicIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("subtenantId") var subtenantId: Int? = null,
    @SerializedName("gradeId") var gradeId: Int? = null,
    @SerializedName("flashcardCount") var flashcardCount: Int? = null
)

data class Front(
    @SerializedName("text") var text: String? = null,
    @SerializedName("imageURL") var imageURL: String? = null,
    @SerializedName("isImageRequired") var isImageRequired: Boolean? = null,
    @SerializedName("orientation") var orientation: String? = null
)

data class Back(
    @SerializedName("text") var text: String? = null,
    @SerializedName("imageURL") var imageURL: String? = null,
    @SerializedName("isImageRequired") var isImageRequired: Boolean? = null,
    @SerializedName("orientation") var orientation: String? = null
)
