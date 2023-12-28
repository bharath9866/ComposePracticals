package com.example.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.utils.Constants.SWIPE_UP

object SLSharedPreference {


    /**
     * The instance of the shared preferences file.
     */
    var instance: SharedPreferences? = null


    /**
     * Gets the secret shared preferences file for the app.
     *
     * @param context The context of the app.
     * @return The secret shared preferences file.
     */
    private fun getSecretSharedPref(context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.SL_SHAREDPREF, Context.MODE_PRIVATE)


    /**
     * Gets the instance of the shared preferences file for the app.
     *
     * @param context The context of the app.
     * @return The instance of the shared preferences file.
     */
    fun getPreferenceInstance(context: Context): SharedPreferences {
        return instance ?: run {
            getSecretSharedPref(context)
        }
    }


    /**
     * Sets a key-value pair in the shared preferences file.
     *
     * @param keyValuePair The key-value pair to set.
     */
    fun setKeyValuePair(vararg keyValuePair: Pair<String, String>) {
        instance?.edit(true) {
            keyValuePair.forEach { (key, value) ->
                putString(key, value)
            }
        }
    }


    /**
     * Edits the shared preferences file.
     *
     * @param operation The operation to perform on the shared preferences file.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * Puts a key-value pair in the shared preferences file if it doesn't exist, otherwise updates the value on the given [key].
     */
    infix fun String.setPreference(value: Any?) {
        instance?.run {
            when (value) {
                is String? -> edit { it.putString(this@setPreference, value) }
                is Int -> edit { it.putInt(this@setPreference, value) }
                is Boolean -> edit { it.putBoolean(this@setPreference, value) }
                is Float -> edit { it.putFloat(this@setPreference, value) }
                is Long -> edit { it.putLong(this@setPreference, value) }
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }
    }

    /**
     * Removes a key from the shared preferences file.
     *
     * @param key The key to remove.
     */
    fun SharedPreferences.remove(key: String) {
        edit { it.remove(key) }
    }


    /**
     * Clears the shared preferences file.
     */
    fun clearSlPreference() {
        instance?.edit(true) { clear() }
    }

    /**
     * Finds a value on the given key.
     *
     * @param key The key to find.
     * @param defaultValue The default value to return if the key doesn't exist.
     * @return The value on the given key, or the default value if the key doesn't exist.
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * Finds a value on the given key.
     *
     * @param key The key to find.
     * @param defaultValue The default value to return if the key doesn't exist.
     * @return The value on the given key, or the default value if the key doesn't exist.
     */
    inline operator fun <reified T : Any> get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> instance?.getString(key, defaultValue as? String) as T?
            Int::class -> instance?.getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> instance?.getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> instance?.getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> instance?.getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    fun get(key: String): String {
        val value = instance?.get(key, "")
        return value ?: ""
    }

    fun SharedPreferences.hasKey(key: String): Boolean? {
        return instance?.contains(key)
    }

    fun setSwipeUpFlag(flag: Boolean) {
        instance?.edit {
            it.putBoolean(SWIPE_UP, flag)
            it.commit()
        }
    }

    fun getSwipeUpFlag(): Boolean = instance?.getBoolean(SWIPE_UP, false) == true

    fun cmsGradeId() = get(Constants.CMS_GRADE_ID, 0)
    fun cmsGradeName() = get(Constants.CMS_GRADE_NAME)
    fun cmsExamId() = get(Constants.CMS_EXAM_ID, 0)
    fun cmsExamName() = get(Constants.CMS_EXAM_NAME)

    var flashcardBackDest: String
        get() = get(Constants.FLASHCARD_BACK_DEST, "0") ?: "0"
        set(value) = Constants.FLASHCARD_BACK_DEST setPreference value

    var slSubjectId: String
        get() = get(Constants.SUBJECT_ID, "0") ?: "0"
        set(value) = Constants.SUBJECT_ID setPreference value
    var slSubjectName: String
        get() = get(Constants.SUBJECT_NAME, "") ?: ""
        set(value) = Constants.SUBJECT_NAME setPreference value
    var slSelectedSubjectIcon: String?
        get() = get(Constants.SUBJECT_ICON, "") ?: ""
        set(value) = Constants.SUBJECT_ICON setPreference (value ?: "")
    var slChapterId: String
        get() = get(Constants.CHAPTER_ID, "0") ?: "0"
        set(value) = Constants.CHAPTER_ID setPreference value
    var slChapterName: String
        get() = get(Constants.CHAPTER_NAME, "") ?: ""
        set(value) = Constants.CHAPTER_NAME setPreference value
    var slTopicId: String
        get() = get(Constants.TOPIC_ID, "0") ?: "0"
        set(value) = Constants.TOPIC_ID setPreference (value ?: "")
    var slTopicName: String?
        get() = get(Constants.TOPIC_NAME, "") ?: ""
        set(value) = Constants.TOPIC_NAME setPreference (value ?: "")
    var slNavigation: String?
        get() = get(Constants.NAVIGATION, "") ?: ""
        set(value) = Constants.NAVIGATION setPreference (value ?: "")
    var slPlaylistId: Int
        get() = get(Constants.PLAYLISTID, 0) ?: 0
        set(value) = Constants.PLAYLISTID setPreference value
    var slPlaylistName: String
        get() = get(Constants.PLAYLISTNAME, "0") ?: ""
        set(value) = Constants.PLAYLISTNAME setPreference value
    var slTabIndex: Int
        get() = get(Constants.TAB_INDEX, 0) ?: 0
        set(value) = Constants.TAB_INDEX setPreference value
    var isFromLearn: Boolean
        get() = get(Constants.FLAG_LEARN, false) ?: false
        set(value) = Constants.FLAG_LEARN setPreference value
    var isSubscription: Boolean
        get() = get(Constants.LEARN_SUBSCRIPTION, false) ?: false
        set(value) = Constants.LEARN_SUBSCRIPTION setPreference value
    var slSelectedBookId: Int
        get() = get(Constants.SELECTED_BOOK_Id, 0) ?: 0
        set(value) = Constants.SELECTED_BOOK_Id setPreference value
    var slSelectedBookName: String?
        get() = get(Constants.SELECTED_BOOK_Name, null)
        set(value) = Constants.SELECTED_BOOK_Name setPreference value
    var resumeLearningFlag: Boolean
        get() = get(Constants.RESUME_LEARNING_FLAG, false) ?: false
        set(value) = Constants.RESUME_LEARNING_FLAG setPreference value

    var toggleFlag: Boolean
        get() = get(Constants.FLAG_FOR_TOGGLE, false) ?: false
        set(value) = Constants.FLAG_FOR_TOGGLE setPreference value

    var slCoins: Int
        get() = get(Constants.KEY_COINS, 0) ?: 0
        set(value) = Constants.KEY_COINS setPreference value

    var slCouponCode: String?
        get() = get(Constants.KEY_COUPON, null)
        set(value) = Constants.KEY_COUPON setPreference value
    var bookSolutionId: Int?
        get() = get(Constants.BOOK_SOLUTION_ID, null)
        set(value) = Constants.BOOK_SOLUTION_ID setPreference value
    var isALastTopic: Boolean
        get() = get(Constants.IS_A_LAST_TOPIC, false) ?: false
        set(value) = Constants.IS_A_LAST_TOPIC setPreference value

    var flagForResumeCallInLearn: Boolean
        get() = get(Constants.FLAG_FOR_RESUME_CALL_IN_LEARN, false) ?: false
        set(value) = Constants.FLAG_FOR_RESUME_CALL_IN_LEARN setPreference value
}