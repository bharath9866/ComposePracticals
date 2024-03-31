package com.example.adaptivestreamingplayer.urlIssue

import com.google.gson.annotations.SerializedName

data class SubjectResponse(

    @SerializedName("status") var status: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: Data? = Data()

)

data class Data(

    @SerializedName("examId") var examId: Int? = null,
    @SerializedName("examName") var examName: String? = null,
    @SerializedName("examDisplayName") var examDisplayName: String? = null,
    @SerializedName("examDescription") var examDescription: String? = null,
    @SerializedName("examStatusId") var examStatusId: Int? = null,
    @SerializedName("parentNode") var parentNode: ParentNode? = ParentNode(),
    @SerializedName("parentLevel") var parentLevel: String? = null,
    @SerializedName("childLevel") var childLevel: String? = null,
    @SerializedName("childNodes") var childNodes: ArrayList<ChildNodes> = arrayListOf(),
    @SerializedName("navigationData") var navigationData: ArrayList<String> = arrayListOf()

)


data class ParentNode(

    @SerializedName("nodeId") var nodeId: String? = null,
    @SerializedName("nodeName") var nodeName: String? = null,
    @SerializedName("orderId") var orderId: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("gradeIds") var gradeIds: String? = null,
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("totalFlashcardCount") var totalFlashcardCount: Int? = null,
    @SerializedName("totalVideoCount") var totalVideoCount: Int? = null,
    @SerializedName("totalStoryCount") var totalStoryCount: Int? = null,
    @SerializedName("totalDocumentCount") var totalDocumentCount: Int? = null,
    @SerializedName("totalPublishedFlashcardCount") var totalPublishedFlashcardCount: Int? = null,
    @SerializedName("totalPublishedVideoCount") var totalPublishedVideoCount: Int? = null,
    @SerializedName("totalPublishedStoryCount") var totalPublishedStoryCount: Int? = null,
    @SerializedName("totalPublishedDocumentCount") var totalPublishedDocumentCount: Int? = null,
    @SerializedName("totalNotPublishedFlashcardCount") var totalNotPublishedFlashcardCount: Int? = null,
    @SerializedName("totalNotPublishedVideoCount") var totalNotPublishedVideoCount: Int? = null,
    @SerializedName("totalNotPublishedStoryCount") var totalNotPublishedStoryCount: Int? = null,
    @SerializedName("totalNotPublishedDocumentCount") var totalNotPublishedDocumentCount: Int? = null,
    @SerializedName("active") var active: Boolean? = null,
    @SerializedName("examSubjectStatusId") var examSubjectStatusId: String? = null,
    @SerializedName("examSubjectStatusName") var examSubjectStatusName: String? = null,
    @SerializedName("color") var color: String? = null,
    @SerializedName("isChaptersAvailable") var isChaptersAvailable: String? = null

)

data class ChildNodes(

    @SerializedName("nodeId") var nodeId: Int? = null,
    @SerializedName("nodeName") var nodeName: String? = null,
    @SerializedName("orderId") var orderId: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("gradeIds") var gradeIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("icon") var icon: String? = null,
    @SerializedName("totalFlashcardCount") var totalFlashcardCount: Int? = null,
    @SerializedName("totalVideoCount") var totalVideoCount: Int? = null,
    @SerializedName("totalStoryCount") var totalStoryCount: Int? = null,
    @SerializedName("totalDocumentCount") var totalDocumentCount: Int? = null,
    @SerializedName("totalPublishedFlashcardCount") var totalPublishedFlashcardCount: Int? = null,
    @SerializedName("totalPublishedVideoCount") var totalPublishedVideoCount: Int? = null,
    @SerializedName("totalPublishedStoryCount") var totalPublishedStoryCount: Int? = null,
    @SerializedName("totalPublishedDocumentCount") var totalPublishedDocumentCount: Int? = null,
    @SerializedName("totalNotPublishedFlashcardCount") var totalNotPublishedFlashcardCount: Int? = null,
    @SerializedName("totalNotPublishedVideoCount") var totalNotPublishedVideoCount: Int? = null,
    @SerializedName("totalNotPublishedStoryCount") var totalNotPublishedStoryCount: Int? = null,
    @SerializedName("totalNotPublishedDocumentCount") var totalNotPublishedDocumentCount: Int? = null,
    @SerializedName("active") var active: Boolean? = null,
    @SerializedName("examSubjectStatusId") var examSubjectStatusId: Int? = null,
    @SerializedName("examSubjectStatusName") var examSubjectStatusName: String? = null,
    @SerializedName("color") var color: String? = null,
    @SerializedName("groups") var groups: ArrayList<Groups> = arrayListOf(),
    @SerializedName("isChaptersAvailable") var isChaptersAvailable: Boolean? = null

)


data class Groups(

    @SerializedName("tenantId") var tenantId: Int? = null,
    @SerializedName("tenantName") var tenantName: String? = null,
    @SerializedName("tenantCode") var tenantCode: String? = null,
    @SerializedName("createdOn") var createdOn: String? = null,
    @SerializedName("modifiedOn") var modifiedOn: String? = null,
    @SerializedName("status") var status: String? = null

)