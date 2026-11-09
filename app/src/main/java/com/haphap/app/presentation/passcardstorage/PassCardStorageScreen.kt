package com.haphap.app.presentation.passcardstorage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.storage.PassCardStorageModel
import com.haphap.app.presentation.passcardstorage.component.PassCardStorageEmptyComponent
import com.haphap.app.presentation.passcardstorage.component.PassCardStorageItem
import com.haphap.app.presentation.passcardstorage.component.PassCardStorageTitle
import com.haphap.app.presentation.passcardstorage.component.PassCardStorageTopBar
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PassCardStorageScreen(
    passCards: List<PassCardStorageModel>,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPassCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = HapHapTheme.colors.white,
        topBar = {
            PassCardStorageTopBar(
                onBackClick = onBackClick,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
        ) {
            PassCardStorageTitle(description = "합격카드를 모았어요. 언제든 꺼내볼 수 있어요.")

            Spacer(modifier = Modifier.height(12.dp))

            if (passCards.isEmpty()) {
                Spacer(modifier = Modifier.height(101.dp))

                PassCardStorageEmptyComponent(onRegisterClick = onRegisterClick)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(
                        items = passCards,
                        key = { it.id }
                    ) {
                        PassCardStorageItem(
                            imageUrl = it.imageUrl,
                            companyName = it.companyName,
                            category = it.category,
                            title = it.title,
                            onItemClick = { onPassCardClick(it.id) },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCardStorageScreenPreview() {
    HapHapTheme {
        PassCardStorageScreen(
            passCards = persistentListOf(
                PassCardStorageModel(
                    id = 1,
                    imageUrl = "",
                    companyName = "카카오",
                    category = "개발/데이터",
                    title = "AI 서비스 기획",
                ),
                PassCardStorageModel(
                    id = 2,
                    imageUrl = "",
                    companyName = "카카오",
                    category = "개발/데이터",
                    title = "AI 서비스 기획",
                ),
            ),
            onBackClick = {},
            onRegisterClick = {},
            onPassCardClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCardStorageScreenEmptyPreview() {
    HapHapTheme {
        PassCardStorageScreen(
            passCards = persistentListOf(),
            onBackClick = {},
            onRegisterClick = {},
            onPassCardClick = {},
        )
    }
}