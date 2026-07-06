package com.haphap.app.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType

/**
 * HapHap 공용 휠 피커 바텀시트.
 *
 * [content]는 [HapHapPicker] 휠들을 나열하는 자리로, 내부적으로 이미
 * 휠 간격/정렬이 맞춰진 [Row] 안에서 호출됩니다.
 * 각 [HapHapPicker]에 `onRowHeightMeasured`를 연결하면, 그 값들 중
 * 가장 큰 값으로 가운데 하이라이트 배경 높이가 맞춰집니다.
 *
 * @param onDismissRequest 바텀시트를 닫아야 할 때(바깥 영역 터치 등) 호출되는 콜백
 * @param onCancelClick "취소" 버튼 클릭 시 호출되는 콜백
 * @param onConfirmClick "확인" 버튼 클릭 시 호출되는 콜백
 * @param modifier Modifier
 * @param sheetState 바텀시트의 펼침/닫힘 상태. 기본값은 half-expanded 없이
 * 바로 완전히 펼쳐지는 상태 (기본값: [rememberModalBottomSheetState] with `skipPartiallyExpanded = true`)
 * @param content [HapHapPicker] 휠들을 나열할 내용. 이미 [Row] 안에서 호출되므로 별도
 * `Row` 없이 휠들을 바로 나열하면 되고, 각 휠에 인자로 받은 `onRowHeightMeasured`를
 * 연결해주면 가운데 하이라이트 배경 높이가 자동으로 맞춰집니다.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HapHapBottomSheet(
    onDismissRequest: () -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    content: @Composable (onRowHeightMeasured: (Dp) -> Unit) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = HapHapTheme.colors.white,
        // todo scrimColor = ,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 33.dp),
            content = {
                var rowHeight by remember { mutableStateOf(0.dp) }

                Box {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .height(rowHeight)
                            .background(
                                color = HapHapTheme.colors.gray100,
                                shape = RoundedCornerShape(8.dp),
                            )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 46.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                    ) {
                        content { measuredHeight -> rowHeight = maxOf(rowHeight, measuredHeight) }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    HapHapBasicButton(
                        text = "취소",
                        textStyle = HapHapTheme.typography.body.b18,
                        colorType = ButtonType.Cancel,
                        onClick = onCancelClick,
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    HapHapBasicButton(
                        text = "확인",
                        textStyle = HapHapTheme.typography.body.b18,
                        colorType = ButtonType.Primary(enabled = true),
                        onClick = onConfirmClick,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HapHapBottomSheetPreview() {
    HapHapTheme {
        HapHapBottomSheet(
            onDismissRequest = {},
            onCancelClick = {},
            onConfirmClick = {},
            content = {},
        )
    }
}