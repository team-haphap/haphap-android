package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.bottomsheet.HapHapDateBottomSheet
import com.haphap.app.core.designsystem.component.bottomsheet.HapHapTimeBottomSheet
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun RegisterEnterDateTime(
    contactDate: String?,
    onDateSelected: (LocalDate) -> Unit,
    contactTime: String?,
    onTimeSelected: (LocalTime) -> Unit,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH시 mm분")

    Row(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "연락받은 날짜",
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray400,
            )

            Spacer(modifier = Modifier.height(10.dp))

            RegisterEnterDate(
                value = contactDate?.let { LocalDate.parse(it).format(dateFormatter) }.orEmpty(),
                placeholder = "연도.월.일",
                onDateSelected = onDateSelected,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "시간대",
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray400,
            )

            Spacer(modifier = Modifier.height(10.dp))

            RegisterEnterTime(
                value = contactTime?.let { LocalTime.parse(it).format(timeFormatter) }.orEmpty(),
                placeholder = "00시 00분",
                onTimeSelected = onTimeSelected,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun RegisterEnterDate(
    value: String,
    placeholder: String,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    var pendingDate by rememberSaveable { mutableStateOf<LocalDate?>(null) }

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

@Composable
fun RegisterEnterTime(
    value: String,
    placeholder: String,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    var pendingTime by rememberSaveable { mutableStateOf<LocalTime?>(null) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = { isBottomSheetVisible = true })
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = value.ifBlank { placeholder },
            modifier = Modifier.weight(1f),
            color = if (value.isBlank()) HapHapTheme.colors.gray400 else HapHapTheme.colors.gray600,
            style = HapHapTheme.typography.body.sb14,
            textAlign = TextAlign.Center
        )
    }

    if (isBottomSheetVisible) {
        HapHapTimeBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            onCancelClick = { isBottomSheetVisible = false },
            onConfirmClick = {
                pendingTime?.let(onTimeSelected)
                isBottomSheetVisible = false
            },
            onTimeSelected = { pendingTime = it },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterEnterDateTimePreview() {
    HapHapTheme {
        var contactDate by rememberSaveable { mutableStateOf<LocalDate?>(null) }
        var contactTime by rememberSaveable { mutableStateOf<LocalTime?>(null) }

        Column {
            RegisterEnterDateTime(
                contactDate = contactDate.toString(),
                onDateSelected = { contactDate = it },
                contactTime = contactTime.toString(),
                onTimeSelected = { contactTime = it },
            )
        }
    }
}