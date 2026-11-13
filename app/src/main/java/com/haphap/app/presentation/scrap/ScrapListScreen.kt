package com.haphap.app.presentation.scrap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.presentation.scrap.ScrapListContract.SideEffect.OnShowToast
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.scrap.ScrapModel
import com.haphap.app.presentation.scrap.component.ScrapDescription
import com.haphap.app.presentation.scrap.component.ScrapEmptyComponent
import com.haphap.app.presentation.scrap.component.ScrapItem
import com.haphap.app.presentation.scrap.component.ScrapTopBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ScrapListRoute(
    navigateBack: () -> Unit,
    navigateToJobDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScrapListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val showToast = LocalToastTrigger.current

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.getScraps()
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is OnShowToast -> showToast(sideEffect.message, sideEffect.isAlarm)
                }
            }
        }
    }

    ScrapListScreen(
        scrapList = uiState.scrapList,
        isEmpty = uiState.isEmpty,
        onBackClick = navigateBack,
        onItemClick = navigateToJobDetail,
        onScrapClick = viewModel::onScrapClick,
        modifier = modifier,
    )
}

@Composable
fun ScrapListScreen(
    scrapList: ImmutableList<ScrapModel>,
    isEmpty: Boolean,
    onBackClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        ScrapTopBar(
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(10.dp))

        ScrapDescription()

        if (isEmpty) {
            ScrapEmptyComponent()
            return@Column
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(
                items = scrapList,
                key = { it.postingId },
            ) { scrap ->
                ScrapItem(
                    imageUrl = scrap.logoImageUrl,
                    companyName = scrap.companyName,
                    category = scrap.category,
                    title = scrap.title,
                    isScrapped = scrap.isScrapped,
                    onItemClick = { onItemClick(scrap.postingId) },
                    onScrapClick = { onScrapClick(scrap.postingId) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrapListScreenPreview() {
    HapHapTheme {
        ScrapListScreen(
            scrapList = persistentListOf(
                ScrapModel(
                    postingId = 1,
                    logoImageUrl = "",
                    companyName = "카카오",
                    category = "개발/데이터",
                    title = "2026 신입 공개채용",
                    isScrapped = false,
                ),
                ScrapModel(
                    postingId = 2,
                    logoImageUrl = "",
                    companyName = "카카오",
                    category = "개발/데이터",
                    title = "2026 신입 공개채용",
                ),
                ScrapModel(
                    postingId = 3,
                    logoImageUrl = "",
                    companyName = "카카오",
                    category = "개발/데이터",
                    title = "2026 신입 공개채용",
                ),
            ),
            isEmpty = false,
            onBackClick = {},
            onItemClick = {},
            onScrapClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrapListScreenEmptyPreview() {
    HapHapTheme {
        ScrapListScreen(
            scrapList = persistentListOf(),
            isEmpty = true,
            onBackClick = {},
            onItemClick = {},
            onScrapClick = {},
        )
    }
}
