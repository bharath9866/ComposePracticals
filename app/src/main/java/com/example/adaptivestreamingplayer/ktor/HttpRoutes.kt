package com.example.adaptivestreamingplayer.ktor

import io.ktor.http.URLBuilder

object HttpRoutes {

    private const val BASE_URL = "https://gatewaystaging.devinfinitylearn.in"
    const val POSTS = "$BASE_URL/api/v1/user/meta/login"

    private const val CMS_BASE_URL = "https://cmsapi.devinfinitylearn.in"
    private const val PLAYLIST = "$CMS_BASE_URL/video_playlist/get/videos/"
    private const val SUBJECTS = "$CMS_BASE_URL/examtoc/v2/getSubjectsForExam/"

    fun getPlaylistUrl(examId: String, subtenantId: String, playlistTypeId: String) =
        "$PLAYLIST$examId/$subtenantId/$playlistTypeId"

    fun getSubjects(examId:String, subtenantId: String, tenantId:String, status:String) =
        "$SUBJECTS$examId?tenantIds=$tenantId&subtenantIds=$subtenantId&status=$status"


    const val PIXABAY_URL = "https://pixabay.com"
}