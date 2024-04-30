package com.example.adaptivestreamingplayer.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.adaptivestreamingplayer.ktor.dto.OtpResponse
import com.example.adaptivestreamingplayer.ktor.dto.UserDto
import com.example.adaptivestreamingplayer.utils.Constants.KEY_LOGIN_DATA
import com.google.gson.Gson

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
    private infix fun String.setPreference(value: Any?) {
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

    fun setLoginData(response: OtpResponse) {
        val gson = Gson()
        val value = gson.toJson(response)
        KEY_LOGIN_DATA setPreference value
    }

    fun getLoginData(): OtpResponse? {
        val gson = Gson()
        val loginData = instance?.get(KEY_LOGIN_DATA, "")
        if (!loginData.isNullOrEmpty()) {
            return gson.fromJson(loginData, OtpResponse::class.java)
        }
        return null
    }

    var accessToken: String
        get() = get(Constants.ACCESS_TOKEN, "") ?: ""
        set(value) = Constants.ACCESS_TOKEN setPreference value

    fun userId() = getLoginData()?.userDto?.userId?.toString()

    val userDto: UserDto?
        get() = getLoginData()?.userDto


}