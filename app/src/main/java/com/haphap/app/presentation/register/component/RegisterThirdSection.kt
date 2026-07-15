package com.haphap.app.presentation.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.haphap.app.presentation.register.type.NotificationChannelType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun RegisterThirdSection(
    contactDate: String?,
    onDateSelected: (LocalDate) -> Unit,
    contactTime: String?,
    onTimeSelected: (LocalTime) -> Unit,
    selectedChannels: ImmutableList<NotificationChannelType>,
    onChannelToggled: (NotificationChannelType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "날짜",
            style = HapHapTheme.typography.body.b18,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(9.dp))

        RegisterEnterDateTime(
            contactDate = contactDate,
            onDateSelected = onDateSelected,
            contactTime = contactTime,
            onTimeSelected = onTimeSelected,
        )

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
}

@Composable
private fun NotificationChannelGrid(
    selectedChannels: ImmutableList<NotificationChannelType>,
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

                repeat(COLUMN_COUNT - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

private const val COLUMN_COUNT = 2

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterThirdSectionPreview() {
    var contactDate by remember { mutableStateOf<LocalDate?>(null) }
    var contactTime by remember { mutableStateOf<LocalTime?>(null) }
    var selectedChannels by remember { mutableStateOf(persistentListOf<NotificationChannelType>()) }

    HapHapTheme {
        RegisterThirdSection(
            contactDate = contactDate.toString(),
            onDateSelected = { contactDate = it },
            contactTime = contactTime.toString(),
            onTimeSelected = { contactTime = it },
            selectedChannels = selectedChannels,
            onChannelToggled = { channel ->
                selectedChannels = if (selectedChannels.contains(channel)) {
                    selectedChannels.remove(channel)
                } else {
                    selectedChannels.add(channel)
                }
            },
        )
    }
}