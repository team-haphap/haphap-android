package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapColors
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

private enum class RegisterButtonSelectionState {
    SELECTED,
    UNSELECTED,
}

private data class RegisterButtonStyle(
    val backgroundColor: Color,
    val textColor: Color,
)

private fun RegisterButtonSelectionState.toStyle(colors: HapHapColors): RegisterButtonStyle = when (this) {
    RegisterButtonSelectionState.SELECTED -> RegisterButtonStyle(
        backgroundColor = colors.sub100,
        textColor = colors.primary500,
    )
    RegisterButtonSelectionState.UNSELECTED -> RegisterButtonStyle(
        backgroundColor = colors.gray100,
        textColor = colors.gray400,
    )
}

@Composable
fun RegisterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = (if (isSelected) RegisterButtonSelectionState.SELECTED else RegisterButtonSelectionState.UNSELECTED)
        .toStyle(HapHapTheme.colors)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = style.backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.body.sb14,
            color = style.textColor,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterButtonPreview() {
    HapHapTheme {
        var selectedIndex by remember { mutableStateOf<Int?>(null) }
        val texts = listOf("인적성", "서류", "면접", "코딩테스트")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            texts.chunked(2).forEachIndexed { rowIndex, rowTexts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    rowTexts.forEachIndexed { columnIndex, text ->
                        val index = rowIndex * 2 + columnIndex
                        RegisterButton(
                            text = text,
                            isSelected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}