package com.haphap.app.presentation.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun MyPageProfileSection(
    profileImageUrl: String,
    nickName: String,
    point: Int,
    onProfileEditClick: () -> Unit,
    onPointClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UrlImage(
            url = profileImageUrl,
            placeholderDrawable = R.drawable.img_profile,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "${nickName}님",
                style = HapHapTheme.typography.subtitle.b22,
                color = HapHapTheme.colors.gray800,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "프로필 수정하러 가기",
                style = HapHapTheme.typography.caption.m12,
                color = HapHapTheme.colors.gray400,
                modifier = Modifier.noRippleClickable(onClick = onProfileEditClick),
            )
        }

        PointChip(
            point = point,
            modifier = Modifier.noRippleClickable(onClick = onPointClick),
        )
    }
}

@Composable
private fun PointChip(
    point: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = HapHapTheme.colors.gray100)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_home_point_14),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(14.dp),
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "%,dP".format(point),
            style = HapHapTheme.typography.body.m14,
            color = HapHapTheme.colors.gray400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageProfileSectionPreview() {
    HapHapTheme {
        MyPageProfileSection(
            profileImageUrl = "",
            nickName = "익명의 조르디",
            point = 300,
            onProfileEditClick = {},
            onPointClick = {},
        )
    }
}
