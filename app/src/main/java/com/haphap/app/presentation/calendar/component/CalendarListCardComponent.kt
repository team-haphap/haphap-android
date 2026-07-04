package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun CalendarListCardComponent(
    titleText: String,
    stage: String,
    participantCount: Int,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(HapHapTheme.colors.white)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CalendarListCardContent(
            titleText = titleText,
            stage = stage,
            participantCount = participantCount,
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = Color.Unspecified)
                .border(
                    shape = RoundedCornerShape(8.dp),
                    color = HapHapTheme.colors.gray100,
                    width = 1.dp,
                )
        ) {
            UrlImage(
                url = imageUrl,
                placeholderDrawable = R.drawable.img_calendar_kakao,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun CalendarListCardContent(
    titleText: String,
    stage: String,
    participantCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = titleText,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row() {
            CalendarStatusChip(
                chipText = "$stage 발표 예상",
                isExpectedStage = true,
            )

            Spacer(modifier = Modifier.width(6.dp))

            CalendarStatusChip(
                chipText = "${participantCount}명 참여중",
                isExpectedStage = false,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "과거 유사 공고 흐름을 바탕으로 예상했어요!",
            style = HapHapTheme.typography.caption.r10,
            color = HapHapTheme.colors.gray500,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CalendarListCardComponentPreview() {
    HapHapTheme {
        CalendarListCardComponent(
            titleText = "2026 신입 개발자 공개채용",
            stage = "서류",
            participantCount = 32,
            imageUrl = "",
            modifier = Modifier.padding(16.dp),
        )
    }
}
