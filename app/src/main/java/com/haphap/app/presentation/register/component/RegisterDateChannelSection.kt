package com.haphap.app.presentation.register.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.presentation.register.NotificationChannelType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ColumnScope.RegisterDateChannelSection(
    contactDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    contactTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
    selectedChannels: PersistentList<NotificationChannelType>,
    onChannelToggled: (NotificationChannelType) -> Unit,
    isNextEnabled: Boolean,
    onNextClick: () -> Unit,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH시 mm분")

    Column(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "날짜",
            style = HapHapTheme.typography.body.b18,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(9.dp))

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
                    value = contactDate?.format(dateFormatter).orEmpty(),
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
                    value = contactTime?.format(timeFormatter).orEmpty(),
                    placeholder = "00시 00분",
                    onTimeSelected = onTimeSelected,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "알림 채널",
            style = HapHapTheme.typography.body.b18,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(9.dp))

        NotificationChannelGrid(
            selectedChannels = selectedChannels,
            onChannelToggled = onChannelToggled,
        )
    }

    HapHapBasicButton(
        text = "다음",
        textStyle = HapHapTheme.typography.body.b18,
        colorType = ButtonType.Primary(enabled = isNextEnabled),
        onClick = onNextClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
    )

}

@Composable
private fun NotificationChannelGrid(
    selectedChannels: PersistentList<NotificationChannelType>,
    onChannelToggled: (NotificationChannelType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        NotificationChannelType.entries.chunked(COLUMN_COUNT).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                rowItems.forEach { channel ->
                    key(channel) {
                        HapHapBasicButton(
                            text = channel.text,
                            textStyle = HapHapTheme.typography.body.sb14,
                            colorType = if (selectedChannels.contains(channel)) {
                                ButtonType.Selected
                            } else {
                                ButtonType.UnSelected
                            },
                            onClick = { onChannelToggled(channel) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                repeat(COLUMN_COUNT - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

private const val COLUMN_COUNT = 2

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterDateChannelSectionPreview() {
    var contactDate by remember { mutableStateOf<LocalDate?>(null) }
    var contactTime by remember { mutableStateOf<LocalTime?>(null) }
    var selectedChannels by remember { mutableStateOf(persistentListOf<NotificationChannelType>()) }

    HapHapTheme {
        RegisterStepScaffold(
            onBackClick = {},
            progress = 3,
            totalSteps = 3,
        ) {
            RegisterDateChannelSection(
                contactDate = contactDate,
                onDateSelected = { contactDate = it },
                contactTime = contactTime,
                onTimeSelected = { contactTime = it },
                selectedChannels = selectedChannels,
                onChannelToggled = { channel ->
                    selectedChannels = if (selectedChannels.contains(channel)) {
                        selectedChannels.remove(channel)
                    } else {
                        selectedChannels.add(channel)
                    }
                },
                isNextEnabled = contactDate != null && contactTime != null,
                onNextClick = {},
            )
        }
    }
}