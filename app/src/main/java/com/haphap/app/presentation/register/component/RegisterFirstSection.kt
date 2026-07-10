package com.haphap.app.presentation.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.RegisterUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RegisterFirstSection(
    announceList: ImmutableList<RegisterDropDownItemModel>,
    selectedAnnounce: RegisterDropDownItemModel?,
    onAnnounceSelected: (RegisterDropDownItemModel) -> Unit,
    processList: ImmutableList<RegisterProcessModel>,
    processListUiState: RegisterUiState,
    selectedProcessId: Int?,
    onProcessSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "공고",
            style = HapHapTheme.typography.body.b18,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(9.dp))

        RegisterDropDown(
            items = announceList,
            selectedItem = selectedAnnounce,
            onItemSelected = onAnnounceSelected,
            placeholder = "원하는 공고를 선택해주세요",
            modifier = Modifier.fillMaxWidth(),
        )

        if (selectedAnnounce != null) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "전형",
                style = HapHapTheme.typography.body.b18,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(9.dp))

            if (processListUiState == RegisterUiState.Success) {
                ProcessButtonGrid(
                    processList = processList,
                    selectedProcessId = selectedProcessId,
                    onProcessSelected = onProcessSelected,
                )
            }
        }
    }
}

@Composable
private fun ProcessButtonGrid(
    processList: ImmutableList<RegisterProcessModel>,
    selectedProcessId: Int?,
    onProcessSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        processList.chunked(COLUMN_COUNT).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                rowItems.forEach { process ->
                    key(process.id) {
                        HapHapBasicButton(
                            text = process.text,
                            textStyle = HapHapTheme.typography.body.sb14,
                            colorType = if (process.id == selectedProcessId) {
                                ButtonType.Selected
                            } else {
                                ButtonType.UnSelected
                            },
                            onClick = { onProcessSelected(process.id) },
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

private const val COLUMN_COUNT = 3

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterFirstSectionPreview() {
    val announceList = persistentListOf(
        RegisterDropDownItemModel(id = 1, text = "카카오 2026 신입 개발자 공개 채용"),
        RegisterDropDownItemModel(id = 2, text = "네이버 2026 신입 개발자 공개 채용"),
    )
    val processList = persistentListOf(
        RegisterProcessModel(id = 1, text = "서류"),
        RegisterProcessModel(id = 2, text = "AI 역량 검사"),
        RegisterProcessModel(id = 3, text = "1차면접"),
        RegisterProcessModel(id = 4, text = "2차면접"),
        RegisterProcessModel(id = 5, text = "임원면접"),
        RegisterProcessModel(id = 6, text = "최종"),
        RegisterProcessModel(id = 7, text = "기타"),
    )

    var selectedAnnounce by remember { mutableStateOf<RegisterDropDownItemModel?>(announceList.first()) }
    var selectedProcessId by remember { mutableStateOf<Int?>(1) }

    HapHapTheme {
        RegisterFirstSection(
            announceList = announceList,
            selectedAnnounce = selectedAnnounce,
            onAnnounceSelected = { selectedAnnounce = it },
            processList = processList,
            processListUiState = RegisterUiState.Success,
            selectedProcessId = selectedProcessId,
            onProcessSelected = { selectedProcessId = it },
        )
    }
}