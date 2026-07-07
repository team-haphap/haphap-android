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
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalTime

/**
 * 시/분을 휠로 선택하는 시간 선택 바텀시트.
 *
 * 시(00~23)/분(00~59) 휠은 끝까지 스크롤하면 처음으로 순환되는 무한
 * 스크롤로 동작합니다. 선택이 바뀔 때마다 [onTimeSelected]로 현재
 * 선택된 [LocalTime]이 전달됩니다.
 *
 * @param onDismissRequest 바텀시트를 닫아야 할 때 호출되는 콜백
 * @param onCancelClick "취소" 버튼 클릭 시 호출되는 콜백
 * @param onConfirmClick "확인" 버튼 클릭 시 호출되는 콜백
 * @param onTimeSelected 선택된 시간이 바뀔 때마다 호출되는 콜백 (기본값: 아무 동작 안 함)
 * @param initialTime 처음 열렸을 때 선택되어 있을 시간 (기본값: 현재 시간)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HapHapTimeBottomSheet(
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit = {},
    initialTime: LocalTime = LocalTime.now(),
) {
    HapHapBottomSheet(
        onDismissRequest = onDismissRequest,
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
    ) { onRowHeightMeasured ->
        var hour by remember { mutableIntStateOf(initialTime.hour) }
        var minute by remember { mutableIntStateOf(initialTime.minute) }

        val hours = remember { (0..23).toList() }
        val minutes = remember { (0..59).toList() }

        val hourState = rememberPickerState()
        val minuteState = rememberPickerState()

        LaunchedEffect(hourState.selectedItem) {
            hourState.selectedItem.removeSuffix("시").toIntOrNull()?.let { hour = it }
        }
        LaunchedEffect(minuteState.selectedItem) {
            minuteState.selectedItem.removeSuffix("분").toIntOrNull()?.let { minute = it }
        }

        LaunchedEffect(hour, minute) {
            onTimeSelected(LocalTime.of(hour, minute))
        }

        HapHapPicker(
            items = hours.map { "${"%02d".format(it)}시" }.toImmutableList(),
            state = hourState,
            startIndex = hours.indexOf(hour),
            isInfinite = true,
            onItemHeightMeasured = onRowHeightMeasured,
        )
        HapHapPicker(
            items = minutes.map { "${"%02d".format(it)}분" }.toImmutableList(),
            state = minuteState,
            startIndex = minutes.indexOf(minute),
            isInfinite = true,
            onItemHeightMeasured = onRowHeightMeasured,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HapHapTimeBottomSheetPreview() {
    HapHapTheme {
        HapHapTimeBottomSheet(
            onDismissRequest = {},
            onCancelClick = {},
            onConfirmClick = {},
        )
    }
}
