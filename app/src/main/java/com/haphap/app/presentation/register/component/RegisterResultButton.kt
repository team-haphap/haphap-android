package com.haphap.app.presentation.register.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.register.type.PassResultStatusButton

@Composable
fun RegisterResultButton(
    status: PassResultStatusButton,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (isSelected) HapHapTheme.colors.sub300 else HapHapTheme.colors.gray100
    val backgroundColor = if (isSelected) HapHapTheme.colors.sub100 else HapHapTheme.colors.white

    Column(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(
                id = if (isSelected) status.selectedBadgeRes else status.defaultBadgeRes
            ),
            contentDescription = null,
        )

        Text(
            text = status.text,
            style = HapHapTheme.typography.body.sb14,
            color = if (isSelected) HapHapTheme.colors.primary500 else HapHapTheme.colors.gray400,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterResultButtonPreview() {
    var selectedStatus by remember { mutableStateOf(PassResultStatusButton.PASS) }

    HapHapTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PassResultStatusButton.entries.forEach { status ->
                RegisterResultButton(
                    status = status,
                    isSelected = status == selectedStatus,
                    onClick = { selectedStatus = status },
                )
            }
        }
    }
}