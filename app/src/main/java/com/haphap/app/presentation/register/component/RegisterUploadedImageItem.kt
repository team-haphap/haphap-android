package com.haphap.app.presentation.register.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun RegisterUploadedImageItem(
    fileName: String,
    fileSize: String,
    thumbnail: Uri,
    errorMessage: String?,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .padding(end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(11.dp),
        ) {
            UploadedImageThumbnail(
                thumbnail = thumbnail,
                isError = errorMessage != null,
            )

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = fileName,
                    style = HapHapTheme.typography.body.sb14,
                    color = HapHapTheme.colors.gray800,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = errorMessage ?: fileSize,
                    style = HapHapTheme.typography.caption.m12,
                    color = if (errorMessage != null) HapHapTheme.colors.subred
                            else HapHapTheme.colors.gray500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_delete_36),
                contentDescription = null,
                tint = HapHapTheme.colors.gray200,
                modifier = Modifier
                    .size(36.dp)
                    .noRippleClickable(onClick = onRemoveClick),
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = HapHapTheme.colors.gray100,
        )
    }
}

@Composable
private fun UploadedImageThumbnail(
    thumbnail: Uri,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size = 51.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(HapHapTheme.colors.gray100),
        contentAlignment = Alignment.Center,
    ) {
        if (isError) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_alert_53),
                contentDescription = null,
                tint = HapHapTheme.colors.subred,
                modifier = Modifier.size(32.dp),
            )
        } else {
            UrlImage(
                url = thumbnail.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterUploadedImageItemPreview() {
    HapHapTheme {
        RegisterUploadedImageItem(
            fileName = "합격메일_01.png",
            fileSize = "285.6KB",
            thumbnail = Uri.EMPTY,
            errorMessage = null,
            onRemoveClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterUploadedImageItemErrorPreview() {
    HapHapTheme {
        RegisterUploadedImageItem(
            fileName = "합격메일_01.png",
            fileSize = "285.6KB",
            thumbnail = Uri.EMPTY,
            errorMessage = "이미지를 불러오는데 실패했어요.",
            onRemoveClick = {},
        )
    }
}
