package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.register.DropDownItemSelectionState
import com.haphap.app.presentation.register.toStyle

val RegisterDropDownItemHeight = 50.dp

@Composable
fun RegisterDropDownItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = (if (isSelected) DropDownItemSelectionState.SELECTED else DropDownItemSelectionState.UNSELECTED)
        .toStyle(HapHapTheme.colors)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = style.backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 15.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.body.sb14,
            color = style.textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterDropDownItemPreview() {
    HapHapTheme {
        RegisterDropDownItem(
            text = "카카오 2026 신입 개발자 공개 채용",
            isSelected = true,
            onClick = {},
        )
    }
}