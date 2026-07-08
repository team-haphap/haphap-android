package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.StatusChipType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private val AvatarSize = 36
private val AvatarStep = 26

@Composable
fun JobParticipantSection(
    registeredCount: Int,
    profileImages: ImmutableList<String>,
    additionalCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.img_fire),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = "${registeredCount}명 등록 중!",
                style = HapHapTheme.typography.body.b18,
                color = HapHapTheme.colors.gray800,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "이 공고에 지원한 사람들이 자신의 전형 결과를 등록하고 있어요.",
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray600,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            val avatarRowWidth = if (profileImages.isEmpty()) {
                0.dp
            } else {
                AvatarStep.dp * (profileImages.size - 1) + AvatarSize.dp
            }

            Box(modifier = Modifier.width(avatarRowWidth)) {
                profileImages.forEachIndexed { index, imageUrl ->
                    UrlImage(
                        modifier = Modifier
                            .offset(x = AvatarStep.dp * index)
                            .size(AvatarSize.dp)
                            .clip(shape = CircleShape)
                            .border(
                                width = 1.dp,
                                color = HapHapTheme.colors.white,
                                shape = CircleShape,
                            ),
                        url = imageUrl,
                        placeholderDrawable = R.drawable.ic_launcher_background,
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            if (additionalCount > 0) {
                HapHapStatusChip(
                    text = "+$additionalCount",
                    type = StatusChipType.COUNT,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobParticipantSectionPreview() {
    HapHapTheme {
        JobParticipantSection(
            registeredCount = 132,
            profileImages = persistentListOf("", "", "", ""),
            additionalCount = 129,
        )
    }
}