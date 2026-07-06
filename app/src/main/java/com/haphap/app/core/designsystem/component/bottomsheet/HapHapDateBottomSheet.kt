package com.haphap.app.core.designsystem.component.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.haphap.app.core.designsystem.theme.HapHapTheme
import java.time.LocalDate
import java.time.YearMonth

/**
 * 년/월/일을 휠로 선택하는 날짜 선택 바텀시트.
 *
 * 년/월/일 세 휠이 서로 연동되어, 월을 바꾸면 그 달의 실제 일수에 맞춰
 * 일(day) 휠의 선택 가능 범위가 자동으로 갱신됩니다. 선택이 바뀔 때마다
 * [onDateSelected]로 현재 선택된 [LocalDate]가 전달됩니다.
 *
 * @param onDismissRequest 바텀시트를 닫아야 할 때 호출되는 콜백
 * @param onCancelClick "취소" 버튼 클릭 시 호출되는 콜백
 * @param onConfirmClick "확인" 버튼 클릭 시 호출되는 콜백
 * @param onDateSelected 선택된 날짜가 바뀔 때마다 호출되는 콜백 (기본값: 아무 동작 안 함)
 * @param initialDate 처음 열렸을 때 선택되어 있을 날짜 (기본값: 오늘 날짜)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HapHapDateBottomSheet(
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onDateSelected: (LocalDate) -> Unit = {},
    initialDate: LocalDate = LocalDate.now(),
) {
    HapHapBottomSheet(
        onDismissRequest = onDismissRequest,
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
    ) { onRowHeightMeasured ->
        var year by remember { mutableIntStateOf(initialDate.year) }
        var month by remember { mutableIntStateOf(initialDate.monthValue) }
        var day by remember { mutableIntStateOf(initialDate.dayOfMonth) }

        val years = remember { (2000..2030).toList() }
        val months = remember { (1..12).toList() }
        val days = remember(year, month) {
            (1..YearMonth.of(year, month).lengthOfMonth()).map { d -> "${d}일" }
        }

        val yearState = rememberPickerState()
        val monthState = rememberPickerState()
        val dayState = rememberPickerState()

        LaunchedEffect(yearState.selectedItem) {
            yearState.selectedItem.removeSuffix("년").toIntOrNull()?.let { year = it }
        }
        LaunchedEffect(monthState.selectedItem) {
            monthState.selectedItem.removeSuffix("월").toIntOrNull()?.let { month = it }
        }
        LaunchedEffect(dayState.selectedItem) {
            dayState.selectedItem.removeSuffix("일").toIntOrNull()?.let { day = it }
        }

        LaunchedEffect(year, month, day) {
            val lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth()
            onDateSelected(LocalDate.of(year, month, day.coerceAtMost(lastDayOfMonth)))
        }

        HapHapPicker(
            items = years.map { "${it}년" },
            state = yearState,
            startIndex = years.indexOf(year),
            onItemHeightMeasured = onRowHeightMeasured,
        )
        HapHapPicker(
            items = months.map { "${it}월" },
            state = monthState,
            startIndex = months.indexOf(month),
            onItemHeightMeasured = onRowHeightMeasured,
        )
        HapHapPicker(
            items = days,
            state = dayState,
            startIndex = (day - 1).coerceIn(days.indices),
            onItemHeightMeasured = onRowHeightMeasured,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HapHapDateBottomSheetPreview() {
    HapHapTheme {
        HapHapDateBottomSheet(
            onDismissRequest = {},
            onCancelClick = {},
            onConfirmClick = {},
        )
    }
}
