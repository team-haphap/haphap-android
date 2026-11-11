package com.haphap.app.presentation.passcard.storage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.passcard.PassCardStorageModel
import com.haphap.app.presentation.passcard.storage.PassCardStorageContract.SideEffect.NavigateBack
import com.haphap.app.presentation.passcard.storage.PassCardStorageContract.SideEffect.NavigateToPassCardDetail
import com.haphap.app.presentation.passcard.storage.PassCardStorageContract.SideEffect.NavigateToRegister
import com.haphap.app.presentation.passcard.storage.component.PassCardStorageEmptyComponent
import com.haphap.app.presentation.passcard.storage.component.PassCardStorageItem
import com.haphap.app.presentation.passcard.storage.component.PassCardStorageTopBar
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PassCardStorageRoute(
    navigateBack: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToPassCardDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PassCardStorageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateBack -> navigateBack()
                    is NavigateToRegister -> navigateToRegister()
                    is NavigateToPassCardDetail -> navigateToPassCardDetail(sideEffect.id)
                }
            }
        }
    }

    PassCardStorageScreen(
        uiState = uiState,
        onBackClick = viewModel::onBackClick,
        onRegisterClick = viewModel::onRegisterClick,
        onPassCardClick = viewModel::onPassCardClick,
        modifier = modifier,
    )
}

@Composable
fun PassCardStorageScreen(
    uiState: PassCardStorageContract.State,
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
            Text(
                text = "합격카드를 모았어요. 언제든 꺼내볼 수 있어요.",
                style = HapHapTheme.typography.body.sb13,
                color = HapHapTheme.colors.gray400,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (uiState.passCardStorageList.isEmpty()) {
                Spacer(modifier = Modifier.height(101.dp))

                PassCardStorageEmptyComponent(onRegisterClick = onRegisterClick)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(
                        items = uiState.passCardStorageList,
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
            uiState = PassCardStorageContract.State(
                passCardStorageList = persistentListOf(
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
            ),
            onBackClick = {},
            onRegisterClick = {},
            onPassCardClick = {},
        )
    }
}