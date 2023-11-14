package com.davidrevolt.qwitter.core.designsystem.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

@Composable
fun TweetDisplay(
    modifier: Modifier = Modifier,
    displayName: String ="David Manshari",
    profilePictureUri: Uri = Uri.EMPTY,
    content: String = "Blah blah blah...blah blah.",
    mediaUri: List<Uri> = emptyList(),
    commentsCount: Int =10,
    likedByCount: Int=20,
    publishDate: Date = Timestamp.from(Instant.now())
) {
    Column(modifier = modifier) {
        Row {
            ImageLoader(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape)
                    .size(35.dp), imgUri = profilePictureUri
            )
            Text(text = displayName)
        }
        Row {
            Text(text = content)
        }
        Row{
            Text(text = "likes:$likedByCount comments:$commentsCount")
            Text(text = "published at:$publishDate")
        }
    }
}