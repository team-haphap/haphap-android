package com.haphap.app.core.designsystem.component.chip

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
fun FilterChipPreview() {
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
