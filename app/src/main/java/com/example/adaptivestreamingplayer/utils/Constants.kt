package com.example.adaptivestreamingplayer.utils

object Constants {
    const val SL_SHAREDPREF = "sl21_shared_preference"
    const val KEY_TOKEN = "auth_token"
    const val SELF_LEARN_SHARED_PREF_NAME = "self_learn_shared_pref"
    const val USER_NAME = "user_name"
    const val MINUTES = "minutes"
    const val HOURS = "hours"
    const val USER_ID = "user_id"
    const val FROM = "FROM"
    const val INTENT_PAGE_LINK = "INTENT_PAGE_LINK"
    const val KEY_LOGIN_DATA = "login_data"
    const val SWIPE_UP = "SwipeUp"

    const val NAVIGATION = "navigation"
    const val PLAYLISTID = "PlayListId"
    const val PLAYLISTNAME = "PlayListName"
    const val TAB_INDEX = "tab_index"

    const val FLAG_LEARN = "learn"
    const val LEARN_SUBSCRIPTION = "learn subscription"
    const val KEY_COINS = "learn coins"
    const val KEY_COUPON = "learn coupon"

    const val SELECTED_BOOK_Id = "selectedBookId"
    const val SELECTED_BOOK_Name = "selectedBookName"

    const val MEDIA_MP4 = ".mp4"
    const val TENANT_ID = "tenantId"
    const val SUB_TENANT_ID = "subTenantId"
    const val MESSAGE = "message"
    const val ERROR = "error"
    const val USER_GRADE_ID = "grade_id"

    const val RESUME_LEARNING_FLAG = "resume_learning_flag"

    const val CMS_GRADE_ID = "cms_grade_id"
    const val CMS_GRADE_NAME = "cms_grade_name"

    const val LAST_NAME = "last_name"
    const val USER_ROLE_TYPE = "user_role_type"
    const val USER_GRADE_NAME = "grade_name"
    const val ADM_NO = "adm_no"
    const val X_TENANT = "x_tenant"

    // One digit
    const val INTEGER_TYPE = "integer type question"

    const val NUMERIC_TYPE = "numerical value question"


    //Checkbox
    const val MMCQ_TYPE = "multiple correct mcq"

    const val SCMCQ_TYPE = "single correct mcq"
    const val MATRIX_SCMCQ_TYPE = "matrix match single correct"
    const val MATRIX_MMCQ_TYPE = "matrix match multiple correct"
    const val MATRIX_TWO_COLUMN_TYPE = "Matrix-Match Two Column"
    const val ASSERTION_AND_REASON_TYPE = "assertion and reason"


    const val EXAM_ID = "exam_id"
    const val CMS_EXAM_ID = "cms_exam_id"
    const val CMS_EXAM_NAME = "cms_exam_name"
    const val EXAM_NAME = "exam_name"
    const val SUBJECT_ID = "subject_id"
    const val SUBJECT_NAME = "subject_name"
    const val SUBJECT_ICON = "subject_icon"
    const val CHAPTER_ID = "chapter_id"
    const val CHAPTER_NAME = "chapter_name"
    const val LEARN_VIDEO_TYPE_IDS = "videoTypes"
    const val MIN_VIDEO_COMPLETE_PERCENTAGE = "minVideoCompletePercentage"
    const val REVISE_VIDEO_TYPE_IDS = "reviseVideoTypes"

    const val AIR = "air"
    const val PROGRESS_DIALOG = "progressDialog"


    const val TOPIC_ID = "topic_id"
    const val TOPIC_NAME = "topic_name"
    const val SUBTOPIC_ID = "subtopic_id"
    const val FLASHCARD_ID = "flashcard_id"
    const val FLASHCARD_TYPE_ID = "flashcard_type_id"
    const val RECOMMENDED_SUBJECT_ID = -1
    const val INTERVAL_DURATION_FOR_UI = 100L
    const val INTERVAL_DURATION = 30
    const val BASE_CONTENT_URL = "/Content/content/"
    const val PBQ_TYPE = "passage based question"
    const val TF_TYPE = "True/False Type"
    const val SUBJECTIVE_VERY_SHORT = "Subjective Based Very Short Answer"
    const val SUBJECTIVE_SHORT = "Subjective Based Short Answer"
    const val SUBJECTIVE_LONG = "Subjective Based Long Answer"

    const val MAX_NO_QUESTION_EXERCISE = "max_no_question_exercise"
    const val MAX_EXERCISE_COMPLETION_PERCENTAGE = "max_exercise_completion_percentage"
    const val SCHEDULE_ID = "schedule_id"
    const val RULE_ID = "rule_id"
    const val REVISE_BACK_DEST = "revise_back_dest"
    const val PRACTICE_PRACTICE_DEST = "practice_back"
    const val PRACTICE_BACK_DEST = "practice_back"
    const val REVISE_END_DEST = "revise_end_dest"
    const val TARGET_EXAM_ID = "TARGET_EXAM_ID"
    const val TARGET_EXAM_NAME = "TARGET_EXAM_NAME"
    const val PYQ_YEAR = "PYQ_YEAR"
    const val IS_A_LAST_TOPIC = "IS_A_LAST_TOPIC"
    const val FLASHCARD_BACK_DEST = "flashcard_back"
    const val ACCESS_TOKEN = "access_token"
    const val SWITCH_TO_NON_GUIDED = "SwitchToNonGuided"
    const val FLAG_FOR_TOGGLE = "flagForToggle"
    const val FLASHCARD_BOOKMARK = "bookmark"
    const val HISTORY_DEST = "history_dest"

    const val GOAL_MIN_RANK = "goal_min_rank"
    const val GOAL_MAX_RANK = "goal_max_rank"
    const val LAST_UPDATED_ATMS = "last_updated_atms"
    const val TEST_A = "test_a"
    const val TEST_B = "test_b"
    const val TEST_C = "test_c"

    const val VIDEO_ASSET_TYPE = "video"
    const val FLASHCARD_ASSET_TYPE = "flashcard"
    const val QUE_FEEDBACK_ASSET_TYPE = "questionFeedback"
    const val QUE_REPORT_ASSET_TYPE = "questionReport"
    const val VIDEO_SOLUTION_ASSET_TYPE = "videosolution"

    const val LEVELS_IN_PROGRESS = "levelsInProgress"
    const val SUB_TOPIC_COUNT = "subtopicCount"
    const val CURRENT_LEVEL_CORRECT_QUE = "currentLevelCorrectQue"
    const val WATCH_FLASHCARD_COUNT = "watchFlashCardCount"
    const val RESUME_FLASHCARD_COUNT = "resumeFlashcardOrder"
    const val PUBLISHED_FLASHCARD_COUNT = "publishedFlashcardCount"

    /**
     * This flag is used to single call of onCreate LifeCycle in GuidedTopicScreen.kt
     */
    const val FLAG_FOR_SINGLE_ON_CREATE = "onCreate"
    const val IS_TOOL_TIP_POP = "isToolTipPop"
    const val SCROLL_Y_OFFSET = "scrollYOffset"
    const val GUIDED_LEARN_TOGGLE_VISIBLE = "guided_learn_toggle_visible"
    const val BOOK_SOLUTION_ID = "BOOK_SOLUTION_ID"
    const val FLAG_FOR_RESUME_CALL_IN_LEARN = "flagForResumeCallInLearn"
}