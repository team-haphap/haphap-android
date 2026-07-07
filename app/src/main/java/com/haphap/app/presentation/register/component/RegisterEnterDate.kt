package com.haphap.app.presentation.register.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.bottomsheet.HapHapDateBottomSheet
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RegisterEnterDate(
    value: String,
    placeholder: String,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var pendingDate by remember { mutableStateOf<LocalDate?>(null) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = { isBottomSheetVisible = true })
            .padding(vertical = 12.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = value.ifBlank { placeholder },
            modifier = Modifier.weight(1f),
            color = if (value.isBlank()) HapHapTheme.colors.gray400 else HapHapTheme.colors.gray600,
            style = HapHapTheme.typography.body.sb14,
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_register_calendar_18),
            contentDescription = null,
            tint = if (value.isBlank()) HapHapTheme.colors.gray400 else HapHapTheme.colors.gray600,
        )
    }

    if (isBottomSheetVisible) {
         HapHapDateBottomSheet(
             onDismissRequest = { isBottomSheetVisible = false },
             onCancelClick = { isBottomSheetVisible = false },
             onConfirmClick = {
                 pendingDate?.let(onDateSelected)
                 isBottomSheetVisible = false
             },
             onDateSelected = { pendingDate = it },
         )
     }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterEnterDatePreview() {
    var selectedDate by remember { mutableStateOf("") }
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy.MM.dd") }

    HapHapTheme {
        Row(
            modifier = Modifier.padding(30.dp)
        ) {
            RegisterEnterDate(
                value = selectedDate,
                placeholder = "연도.월.일",
                onDateSelected = { date -> selectedDate = date.format(formatter) },
            )
        }
    }
}