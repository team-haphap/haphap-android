package com.haphap.app.presentation.mypage.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun ProfileImageSection(
    imageUrl: String,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(vertical = 12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(HapHapTheme.colors.gray200)
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray300,
                    shape = CircleShape,
                ),
        ) {
            if (imageUrl.isNotBlank()) {
                UrlImage(
                    url = imageUrl,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 10.dp, y = 8.dp)
                .size(45.dp)
                .clip(CircleShape)
                .background(HapHapTheme.colors.gray300)
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray200,
                    shape = CircleShape,
                )
                .clickable(onClick = onImageClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_profile_camera_29),
                contentDescription = null,
                modifier = Modifier.size(29.dp),
                tint = HapHapTheme.colors.white,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileImageSectionPreview() {
    HapHapTheme {
        ProfileImageSection(
            imageUrl = "",
            onImageClick = {},
        )
    }
}