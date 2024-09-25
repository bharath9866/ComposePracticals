package com.example.adaptivestreamingplayer.ktor

import com.example.adaptivestreamingplayer.BuildConfig

object HttpRoutes {

    const val POSTS = "${BuildConfig.GATEWAY_BASE_URL}api/v1/user/meta/login"

    private const val PLAYLIST = "${BuildConfig.CMS_BASE_URL}video_playlist/get/videos/"
    private const val SUBJECTS = "${BuildConfig.CMS_BASE_URL}examtoc/v2/getSubjectsForExam/"

    fun getPlaylistUrl(examId: String, subtenantId: String, playlistTypeId: String) =
        "$PLAYLIST$examId/$subtenantId/$playlistTypeId"

    fun getSubjects(examId:String, subtenantId: String, tenantId:String, status:String) =
        "$SUBJECTS$examId?tenantIds=$tenantId&subtenantIds=$subtenantId&status=$status"


    const val PIXABAY_URL = "https://pixabay.com"
}