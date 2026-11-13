package com.haphap.app.presentation.register.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RegisterUploadedImageRow(
    images: ImmutableList<Uri>,
    modifier: Modifier = Modifier,
) {
    val spacing = if (images.size == 2) 12.dp else 8.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(101.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
    ) {
        images.forEach { image ->
            key(image) {
                UrlImage(
                    url = image.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(6.dp)),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterUploadedImageRowPreview() {
    HapHapTheme {
        RegisterUploadedImageRow(
            images = persistentListOf(
                Uri.parse("content://preview/1"),
                Uri.parse("content://preview/2"),
                Uri.parse("content://preview/3"),
            ),
        )
    }
}
