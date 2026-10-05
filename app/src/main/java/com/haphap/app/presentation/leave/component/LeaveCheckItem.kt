package com.haphap.app.presentation.leave.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.textfield.HapHapBasicTextField
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.graphemeLength
import com.haphap.app.core.extensions.noRippleClickable

private const val ETC_REASON_MAX_LENGTH = 150
private val ETC_REASON_HEIGHT_SAMPLE_TEXT = "가".repeat(ETC_REASON_MAX_LENGTH)

@Composable
fun LeaveCheckItem(
    context: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    isEtc: Boolean = false,
    etcReasonState: TextFieldState = rememberTextFieldState(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(
                    onClick = { onCheckedChange(!checked) }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (checked) {
                        R.drawable.ic_check_list_selected_24
                    } else {
                        R.drawable.ic_check_list_default_24
                    }
                ),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = context,
                color = HapHapTheme.colors.gray600,
                style = HapHapTheme.typography.body.sb14,
            )
        }

        if (isEtc && checked) {
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = HapHapTheme.colors.gray100,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .padding(horizontal = 10.dp, vertical = 12.dp),
            ) {
                val textStyle = HapHapTheme.typography.caption.r11
                val textMeasurer = rememberTextMeasurer()
                val density = LocalDensity.current

                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    val maxTextHeight = remember(constraints.maxWidth, textStyle) {
                        val maxTextLayout = textMeasurer.measure(
                            text = ETC_REASON_HEIGHT_SAMPLE_TEXT,
                            style = textStyle,
                            constraints = Constraints(maxWidth = constraints.maxWidth),
                        )
                        with(density) { maxTextLayout.size.height.toDp() }
                    }

                    HapHapBasicTextField(
                        state = etcReasonState,
                        textColor = HapHapTheme.colors.gray500,
                        textStyle = textStyle,
                        placeholder = "최대 150자까지 입력이 가능해요.",
                        placeholderColor = HapHapTheme.colors.gray300,
                        placeholderStyle = textStyle,
                        inputTransformation = InputTransformation {
                            if (asCharSequence().trimStart().graphemeLength() > ETC_REASON_MAX_LENGTH) revertAllChanges()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = maxTextHeight),
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${etcReasonState.text.trimStart().graphemeLength()}/$ETC_REASON_MAX_LENGTH",
                    color = HapHapTheme.colors.gray400,
                    style = HapHapTheme.typography.caption.r11,
                    modifier = Modifier.align(Alignment.End),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun LeaveCheckItemPreview() {
    var checked by remember { mutableStateOf(true) }

    HapHapTheme {
        LeaveCheckItem(
            context = "기타 (직접 입력)",
            onCheckedChange = { checked = it },
            checked = checked,
            isEtc = true,
        )
    }
}
