package com.davidrevolt.qwitter.core.designsystem.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CoilImageReq(modifier: Modifier = Modifier, imgUri: Uri, contentDescription: String ="") {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUri)
            .crossfade(true)
            .build(),

        contentDescription = contentDescription,
        modifier = modifier
    )
}