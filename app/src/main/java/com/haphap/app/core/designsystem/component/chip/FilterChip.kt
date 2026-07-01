package com.haphap.app.core.designsystem.component.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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

/**
 * 필터칩 공통 컴포넌트입니다.
 *
 * 아이콘 또는 텍스트 중 하나를 표시하며,
 * 선택 상태에 따라 배경색과 텍스트 스타일이 변경됩니다.
 *
 * @param iconRes 표시할 아이콘 리소스 ID
 * @param text 표시할 텍스트
 * @param isFilterSelected 필터칩 선택 여부 (true: 배경색 primary100, 텍스트 sb14)
 * @param onFilterClick 필터칩 클릭 시 동작
 *
 */

@Composable
fun FilterChip(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
    text: String? = null,
    isFilterSelected: Boolean = false,
    onFilterClick: () -> Unit = {},
) {
    val backgroundColor =
        if (isFilterSelected) HapHapTheme.colors.primary100 else HapHapTheme.colors.gray100
    val contentColor =
        if (isFilterSelected) HapHapTheme.colors.white else HapHapTheme.colors.gray500
    val textStyle =
        if (isFilterSelected) HapHapTheme.typography.body.sb14 else HapHapTheme.typography.body.m14

    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .noRippleClickable(onClick = onFilterClick)
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when {
            iconRes != null -> Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp),
            )

            text != null -> Text(
                text = text,
                style = textStyle,
                color = contentColor,
            )
        }
    }
}

@Preview
@Composable
private fun FilterChipPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                iconRes = R.drawable.ic_filter_20,
            )
            FilterChip(
                text = "전체",
                isFilterSelected = true,
            )
            FilterChip(
                text = "개발",
            )
        }
    }
}
