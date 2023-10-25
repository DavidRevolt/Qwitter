package com.davidrevolt.qwitter.core.designsystem.components

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.imageLoader
import coil.memory.MemoryCache
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
        modifier = modifier
    )
}

@OptIn(ExperimentalCoilApi::class)
fun clearImageLoaderUriCache(context: Context, imgUri: Uri?) {
    val imageLoader = context.imageLoader
    imageLoader.diskCache?.remove(imgUri.toString())
    imageLoader.memoryCache?.remove(MemoryCache.Key(imgUri.toString()))
}

@OptIn(ExperimentalCoilApi::class)
fun clearImageLoaderCache(context: Context) {
    val imageLoader = context.imageLoader
    imageLoader.diskCache?.clear()
    imageLoader.memoryCache?.clear()
}