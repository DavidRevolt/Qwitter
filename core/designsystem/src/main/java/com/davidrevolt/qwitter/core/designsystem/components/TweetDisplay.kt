package com.davidrevolt.qwitter.core.designsystem.components

import android.net.Uri
import android.text.format.DateUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

@Composable
fun TweetDisplay(
    modifier: Modifier = Modifier,
    displayName: String = "DisplayName",
    profilePictureUri: Uri = Uri.EMPTY,
    content: String = "Blah blah blah...blah blah.",
    mediaUri: List<Uri> = emptyList(),
    commentsCount: Int = 10,
    likedByCount: Int = 20,
    publishDate: Date = Timestamp.from(Instant.now())
) {
    Row(modifier = modifier.fillMaxWidth().border(BorderStroke(2.dp, Color.Red))) {
        // Profile Pic
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageLoader(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape)
                    .size(35.dp), imgUri = profilePictureUri
            )
        }


        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier= Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = displayName, fontWeight = FontWeight.Bold)
                Text(text = dateToTimeAgo(publishDate))
            }
            Row {
                Text(text = content)
            }
            Row(modifier= Modifier.fillMaxWidth()) {
                Text(text = "likes:$likedByCount comments:$commentsCount")
            }
        }

    }

}


fun dateToTimeAgo(date: Date): String {
    val time = date.time
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(
        time,
        now,
        DateUtils.SECOND_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_ALL
    ).toString()
}