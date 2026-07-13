package com.haphap.app.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.textfield.HapHapSearchTextField
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.search.SearchContract.SideEffect.OnShowToast
import com.haphap.app.presentation.search.component.SearchDefaultSection
import com.haphap.app.presentation.search.component.SearchResultSection
import com.haphap.app.presentation.search.component.SearchingSection

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val showToast = LocalToastTrigger.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is OnShowToast -> {
                        showToast.invoke(sideEffect.message, sideEffect.isAlarm)
                    }
                }
            }
        }
    }

    LaunchedEffect(
        uiState.searchResultList,
    ) {
        gridState.scrollToItem(0)
    }

    //Todo: 화면 연결
    SearchScreen(
        state = viewModel.searchInputState,
        uiState = uiState,
        gridState = gridState,
        onLoadMoreSearchList = { viewModel.getSearchResultList(hasNextPage = true) },
        onBackClick = {},
        onSearchClick = viewModel::onSearchClick,
        onRecentItemClick = {
            viewModel.onSearchItemClick(it)
            focusManager.clearFocus()
        },
        onAutoCompleteItemClick = {},
        onRelatedItemClick = {
            viewModel.onSearchItemClick(it)
            focusManager.clearFocus()
        },
        onFilterClick = { viewModel.updateSelectedChips(it) },
        onResultCardClick = {},
        onDeleteClick = viewModel::deleteRecentSearchItem,
        onTrendCardClick = {},
        modifier = modifier,
    )
}


@Composable
private fun SearchScreen(
    state: TextFieldState,
    uiState: SearchContract.State,
    gridState: LazyGridState,
    onLoadMoreSearchList: () -> Unit,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onRecentItemClick: (String) -> Unit,
    onAutoCompleteItemClick: (Int) -> Unit,
    onRelatedItemClick: (String) -> Unit,
    onFilterClick: (String) -> Unit,
    onResultCardClick: (Int) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onTrendCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = HapHapTheme.colors.white)
            .padding(top = 10.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_back_30),
                contentDescription = null,
                tint = HapHapTheme.colors.black,
                modifier = Modifier.noRippleClickable(onClick = onBackClick),
            )

            Spacer(modifier = Modifier.width(10.dp))

            HapHapSearchTextField(
                state = state,
                placeholder = "공고명을 검색해보세요!",
                onSearch = onSearchClick,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        when (uiState.section) {
            SearchSection.Default ->
                SearchDefaultSection(
                    recentSearchList = uiState.recentSearchList,
                    trendJobList = uiState.trendJobList,
                    onRecentItemClick = onRecentItemClick,
                    onDeleteClick = onDeleteClick,
                    onCardClick = onTrendCardClick,
                )

            SearchSection.Searching ->
                SearchingSection(
                    searchAutoCompleteList = uiState.searchAutoCompleteList,
                    relatedKeywordList = uiState.relatedKeywordList,
                    onAutoCompleteItemClick = onAutoCompleteItemClick,
                    onRelatedItemClick = onRelatedItemClick,
                )

            SearchSection.Result ->
                SearchResultSection(
                    searchResultUiState = uiState.searchResultListUiState,
                    listState = gridState,
                    chipList = uiState.categoryChipState.chipList,
                    selectedChips = uiState.categoryChipState.selectedChips,
                    onFilterClick = onFilterClick,
                    searchResultList = uiState.searchResultList,
                    onCardClick = onResultCardClick,
                    onLoadMoreSearchList = onLoadMoreSearchList,
                )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    HapHapTheme {
        SearchScreen(
            state = TextFieldState(),
            uiState = SearchContract.State(),
            gridState = LazyGridState(),
            onRecentItemClick = {},
            onBackClick = {},
            onSearchClick = {},
            onAutoCompleteItemClick = {},
            onFilterClick = {},
            onRelatedItemClick = {},
            onResultCardClick = {},
            onDeleteClick = {},
            onTrendCardClick = {},
            onLoadMoreSearchList = {},
        )
    }
}
