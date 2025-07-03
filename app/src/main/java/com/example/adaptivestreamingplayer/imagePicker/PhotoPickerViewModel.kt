package com.example.adaptivestreamingplayer.imagePicker

import android.content.ClipData.newUri
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import timber.log.Timber

class PhotoPickerViewModel : ViewModel() {
    val selectedUris = mutableStateListOf<Uri>()

    fun updateSelectedUris(context: Context, uri: Uri) {
        Timber.tag("URI")
            .d("PhotoPickerViewModel URI: $uri, byteArray: ${uriToByteArray(context, uri)?.size}")

        selectedUris.clear()
        selectedUris.add(uri)
    }

    fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.readBytes()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun clearPhotoPicker() {
        selectedUris.clear()
    }
}
