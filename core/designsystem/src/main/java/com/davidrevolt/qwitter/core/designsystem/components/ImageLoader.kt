package com.davidrevolt.qwitter.core.designsystem.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * App Image Loader, using Coil library
 * */
@Composable
fun ImageLoader(modifier: Modifier = Modifier, imgUri: Uri?, contentDescription: String ="") {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUri)
            .crossfade(true)
            .build(),

        contentDescription = contentDescription,
        contentScale =  ContentScale.Crop,
        modifier = modifier
    )
}