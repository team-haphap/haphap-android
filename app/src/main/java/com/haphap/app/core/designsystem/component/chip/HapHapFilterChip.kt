package com.haphap.app.core.designsystem.component.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable


sealed interface FilterChipContent {
    data class IconContent(
        @param:DrawableRes val iconRes: Int,
        val iconContentDescription: String? = null
    ) : FilterChipContent

    data class TextContent(val text: String) : FilterChipContent
}

/**
 * 필터칩 공통 컴포넌트입니다.
 *
 * 아이콘 또는 텍스트 중 하나를 표시하며,
 * 선택 상태에 따라 배경색과 텍스트 스타일이 변경됩니다.
 *
 * @param content 표시할 아이콘 또는 텍스트
 * @param onFilterClick 필터칩 클릭 시 동작
 * @param isFilterSelected 필터칩 선택 여부 (true: 배경색 primary100, 텍스트 sb14)
 *
 */

@Composable
fun HapHapFilterChip(
    content: FilterChipContent,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFilterSelected: Boolean = false,
) {
    val backgroundColor =
        if (isFilterSelected) HapHapTheme.colors.primary100 else HapHapTheme.colors.gray100
    val contentColor =
        if (isFilterSelected) HapHapTheme.colors.white else HapHapTheme.colors.gray500
    val textStyle =
        if (isFilterSelected) HapHapTheme.typography.body.sb14 else HapHapTheme.typography.body.m14

    Row(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(backgroundColor)
            .noRippleClickable(onClick = onFilterClick)
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (content) {
            is FilterChipContent.IconContent -> Icon(
                imageVector = ImageVector.vectorResource(id = content.iconRes),
                contentDescription = content.iconContentDescription,
                tint = contentColor,
                modifier = Modifier.size(20.dp),
            )

            is FilterChipContent.TextContent -> Text(
                text = content.text,
                style = textStyle,
                color = contentColor,
            )
        }
    }
}

@Preview
@Composable
private fun HapHapFilterChipPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            HapHapFilterChip(
                content = FilterChipContent.IconContent(
                    iconRes = R.drawable.ic_filter_20,
                    iconContentDescription = "필터"
                ),
                onFilterClick = {}
            )
            HapHapFilterChip(
                content = FilterChipContent.TextContent("전체"),
                isFilterSelected = true,
                onFilterClick = {}
            )
            HapHapFilterChip(
                content = FilterChipContent.TextContent("개발"),
                onFilterClick = {}
            )
        }
    }
}
