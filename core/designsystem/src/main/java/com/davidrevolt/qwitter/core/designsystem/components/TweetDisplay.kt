package com.davidrevolt.qwitter.core.designsystem.components

import android.net.Uri
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidrevolt.qwitter.core.designsystem.icons.QwitterIcons
import java.util.Date

@Composable
fun TweetDisplay(
    modifier: Modifier = Modifier,
    displayName: String,
    profilePictureUri: Uri,
    content: String,
    mediaUri: List<Uri>,
    commentsCount: Int,
    likedByCount: Int,
    publishDate: Date
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // Profile Pic
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageLoader(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(CircleShape)
                    .size(45.dp), imgUri = profilePictureUri
            )
        }


        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = displayName,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = dateToTimeAgo(publishDate))
            }
            Row {
                Column {
                    Text(text = content)
                    mediaUri.forEach { uri ->
                        ImageLoader(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RectangleShape), imgUri = uri
                        )
                    }
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconWithText(icon = QwitterIcons.comments, text = "$commentsCount", onClick = {})
                IconWithText(icon = QwitterIcons.like, text = "$likedByCount", onClick = {})
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

@Composable
private fun IconWithText(icon: ImageVector, text: String, onClick: () -> Unit = {}) {
    val textSize = 16.sp
    val textColor = Color(0xFF666666)


    val myId = "inlineContent"
    val content = buildAnnotatedString {
        append(text)
        // Append a placeholder string "[icon]" and attach an annotation "inlineContent" on it.
        appendInlineContent(myId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            // This tells the [CoreText] to replace the placeholder string "[icon]" by
            // the composable given in the [InlineTextContent] object.
            myId,
            InlineTextContent(
                // Placeholder tells text layout the expected size and vertical alignment of
                // children composable.
                Placeholder(
                    width = textSize,
                    height = textSize,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                // This Icon will fill maximum size, which is specified by the [Placeholder]
                // above. Notice the width and height in [Placeholder] are specified in TextUnit,
                // and are converted into pixel by text layout.
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    tint = textColor
                )
            }
        )
    )
    Text(
        text = content,
        modifier = Modifier.clickable(onClick = onClick),
        color = textColor,
        fontSize = textSize,
        inlineContent = inlineContent
    )
}