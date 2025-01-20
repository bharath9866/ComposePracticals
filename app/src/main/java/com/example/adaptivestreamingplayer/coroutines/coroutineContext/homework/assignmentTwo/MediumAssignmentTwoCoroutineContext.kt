package com.example.adaptivestreamingplayer.coroutines.coroutineContext.homework.assignmentTwo

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MediumAssignmentTwoCoroutineContext(modifier: Modifier = Modifier) {
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var isImagePickerOpens by remember { mutableStateOf(false) }
    var backgroundColor: Color by remember { mutableStateOf(Color.White) }

    val imagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            photoUri = it
            isImagePickerOpens = false
        }

    LaunchedEffect(photoUri) {
        withContext(Dispatchers.IO){
            if (photoUri != null) {
                isLoading = true
                delay(1000L)
                val bitmap = context.contentResolver.openInputStream(photoUri!!).use {
                    BitmapFactory.decodeStream(it)
                }
                val dominantColor = PhotoProcessor.findDominantColor(bitmap)
                isLoading = false
                backgroundColor = Color(dominantColor)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RotatingBoxScreen()
        Spacer(modifier = Modifier.height(64.dp))
        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                if(!isImagePickerOpens) {
                    isImagePickerOpens = true
                    imagePicker.launch(input = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }
        }) {
            Text(text = "Pick Image")
        }
        if (isLoading) {
            Text(text = "Finding dominant color...")
        }
        if (photoUri != null) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context).data(photoUri).build()
            )
            Image(painter, contentDescription = null)
        }
    }
}