package com.example.adaptivestreamingplayer.imagePicker

//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import com.example.adaptivestreamingplayer.utils.Constants
//import com.example.adaptivestreamingplayer.utils.Constants.ACCOUNT_ID
//import com.example.adaptivestreamingplayer.utils.Constants.ACCOUNT_ID_PROPERTY
//import com.example.adaptivestreamingplayer.utils.Constants.ALLOWED_CHARACTERS
//import com.example.adaptivestreamingplayer.utils.Constants.APPLIATION_NAME
//import com.example.adaptivestreamingplayer.utils.Constants.APPLICATION_NAME_PROPERTY
//import com.example.adaptivestreamingplayer.utils.Constants.PROJECT_ID
//import com.example.adaptivestreamingplayer.utils.Constants.PROJECT_ID_PROPERTY
//import com.google.api.client.auth.oauth2.Credential
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
//import com.google.api.client.http.HttpTransport
//import com.google.api.client.http.InputStreamContent
//import com.google.api.client.http.javanet.NetHttpTransport
//import com.google.api.client.json.JsonFactory
//import com.google.api.client.json.jackson2.JacksonFactory
//import com.google.api.services.storage.Storage
//import com.google.api.services.storage.StorageScopes
//import com.google.api.services.storage.model.StorageObject
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import timber.log.Timber
//import java.io.File
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import java.io.InputStream
//import java.io.OutputStream
//import java.net.URLConnection
//import java.util.Properties
//
//class CloudStorage(private val context: Context) { // Made context a val
//    private var storage: Storage? = null
//    private var sProperties: Properties? = null
//
//    // Keep your getStorage() and getProperties() methods as they are.
//    // ... (getStorage and getProperties methods from your original code) ...
//    @Throws(Exception::class)
//    private fun getProperties(): Properties? {
//        if (sProperties == null) {
//            sProperties = Properties()
//            sProperties?.setProperty(PROJECT_ID_PROPERTY, PROJECT_ID)
//            sProperties?.setProperty(APPLICATION_NAME_PROPERTY, APPLIATION_NAME)
//            sProperties?.setProperty(ACCOUNT_ID_PROPERTY, ACCOUNT_ID)
//        }
//        return sProperties
//    }
//
//    private fun getTempPkc12File(): File? {
//        try {
//            context.assets.open("g-smart-253410-94e6eab2e827.p12").let {
//                val pkc12Stream: InputStream = it
//                val tempPkc12File = File.createTempFile("g-smart-253410-94e6eab2e827", "p12")
//                val tempFileStream: OutputStream = FileOutputStream(tempPkc12File)
//                var read = 0
//                val bytes = ByteArray(1024)
//                while ((pkc12Stream.read(bytes).also { read = it }) != -1) {
//                    tempFileStream.write(bytes, 0, read)
//                }
//                tempPkc12File.deleteOnExit() // Ensure the temp file is deleted
//                return tempPkc12File
//            }
//        } catch (e: java.lang.Exception) {
//            class Local
//            Log.d("gthink", "Sub: " + Local::class.java.enclosingMethod?.name + " Error code: " + e.message)
//        }
//        Log.e("gthink", " getTempPkc12File is null")
//        return null
//    }
//
//    private fun getStorage(): Storage? {
//        if(storage == null) {
//            try {
//                val httpTransport: HttpTransport = NetHttpTransport()
//                val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
//                val scopes: ArrayList<String> = arrayListOf()
//                scopes.add(StorageScopes.DEVSTORAGE_FULL_CONTROL)
//                val credential: Credential? = GoogleCredential.Builder()
//                    .setTransport(httpTransport)
//                    .setJsonFactory(jsonFactory)
//                    .setServiceAccountId(getProperties()?.getProperty(ACCOUNT_ID_PROPERTY))
//                    .setServiceAccountPrivateKeyFromP12File(getTempPkc12File())
//                    .setServiceAccountScopes(scopes)
//                    .build()
//                storage = Storage.Builder(httpTransport, jsonFactory, credential)
//                    .setApplicationName(getProperties()?.getProperty(APPLICATION_NAME_PROPERTY))
//                    .build()
//            } catch (e:Exception) {
//                Timber.e(e, "Failed to initialize Cloud Storage")
//                return null // Return null on failure
//            }
//        }
//        return storage
//    }
//
//
//    // Modified uploadFile to be a suspend function
//    suspend fun uploadFile(filePath: String, userNumber: String, imgType: String = "ProfileImage"): String? {
//        // Perform the network operation on the IO dispatcher
//        return withContext(Dispatchers.IO) {
//            try {
//                val currentStorage = getStorage() ?: return@withContext null // Ensure storage is not null
//
//                val file = File(filePath)
//                FileInputStream(file).use { stream -> // Use .use for automatic resource management
//                    val objectMetadata = StorageObject().apply {
//                        bucket = Constants.BUCKET_KEY
//                        contentType = "image/jpeg" // Or determine dynamically if needed
//                    }
//
//                    val contentType = URLConnection.guessContentTypeFromStream(stream)
//                        ?: "application/octet-stream" // Default content type if detection fails
//                    val content = InputStreamContent(contentType, stream)
//
//                    val insert = currentStorage.objects()
//                        .insert(Constants.BUCKET_KEY, objectMetadata, content)
//                    val generatedFileName = generateRandomString(15) + ".jpg"
//                    val cloudPath = "${Constants.BUCKET_FOLDER}/$userNumber/$imgType/$generatedFileName"
//
//                    insert.name = cloudPath
//                    insert.execute()
//
//                    val imageUrl = "https://storage.googleapis.com/${Constants.BUCKET_KEY}/$cloudPath"
//                    Timber.tag("ImageUrl").i("Uploaded to: $imageUrl")
//                    imageUrl // Return the URL
//                }
//            } catch (e: Exception) {
//                Timber.e(e, "Error uploading file to GCP")
//                null // Return null on error
//            }
//        }
//    }
//
//    // Use this version if you are getting the file URI from a ContentResolver (e.g., from a photo picker)
//    suspend fun uploadFileFromUri(fileUri: Uri, userNumber: String, imgType: String = "ProfileImage"): String? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val currentStorage = getStorage() ?: return@withContext null
//
//                context.contentResolver.openInputStream(fileUri)?.use { stream ->
//                    val objectMetadata = StorageObject().apply {
//                        bucket = Constants.BUCKET_KEY
//                        // Try to guess content type from URI, fallback to a default
//                        contentType = context.contentResolver.getType(fileUri) ?: "image/jpeg"
//                    }
//
//                    val content = InputStreamContent(objectMetadata.contentType, stream)
//                    val insert = currentStorage.objects()
//                        .insert(Constants.BUCKET_KEY, objectMetadata, content)
//
//                    val generatedFileName = generateRandomString(15) + ".jpg" // Assuming JPG, adjust if needed
//                    val cloudPath = "${Constants.BUCKET_FOLDER}/$userNumber/$imgType/$generatedFileName"
//
//                    insert.name = cloudPath
//                    insert.execute()
//
//                    val imageUrl = "https://storage.googleapis.com/${Constants.BUCKET_KEY}/$cloudPath"
//                    Timber.tag("ImageUrl").i("Uploaded to: $imageUrl from URI")
//                    imageUrl
//                }
//            } catch (e: Exception) {
//                Timber.e(e, "Error uploading file from URI to GCP")
//                null
//            }
//        }
//    }
//
//
//    private fun generateRandomString(length: Int = 15): String {
//        return (1..length)
//            .map { ALLOWED_CHARACTERS.random() }
//            .joinToString("")
//    }
//}