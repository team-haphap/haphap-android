package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import java.time.LocalTime
// import com.haphap.app.core.designsystem.component.bottomsheet.HapHapTimeBottomSheet

@Composable
fun RegisterEnterTime(
    value: String,
    placeholder: String,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = { isBottomSheetVisible = true })
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = value.ifBlank { placeholder },
            color = if (value.isBlank()) HapHapTheme.colors.gray400 else HapHapTheme.colors.gray600,
            style = HapHapTheme.typography.body.sb14,
        )
    }

    // TODO: BottomSheet 추후 연동
    /*
    if (isBottomSheetVisible) {
         HapHapTimeBottomSheet(
             onDismissRequest = { isBottomSheetVisible = false },
             onCancelClick = { isBottomSheetVisible = false },
             onConfirmClick = { isBottomSheetVisible = false },
             onTimeSelected = onTimeSelected,
         )
     }
     */

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterEnterTimePreview() {
    HapHapTheme {
        Box {
            RegisterEnterTime(
                value = "",
                placeholder = "00시 00분",
                onTimeSelected = {},
            )
        }
    }
}