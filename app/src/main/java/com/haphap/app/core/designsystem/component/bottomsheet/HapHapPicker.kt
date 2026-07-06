package com.haphap.app.core.designsystem.component.bottomsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * 세로로 스크롤되는 휠(wheel) 형태의 아이템 선택 컴포넌트.
 *
 * 정중앙에 위치한 아이템이 선택된 값으로 취급되며, 스크롤을 멈추면 항상
 * 하나의 아이템이 정중앙에 오도록 스냅됩니다. 선택된 값은 [state]의
 * [PickerState.selectedItem]으로 확인할 수 있습니다.
 *
 * @param items 휠에 표시할 아이템 목록
 * @param modifier Modifier
 * @param state 현재 선택된 아이템을 담는 상태. 기본값은 내부에서 새로 생성하며,
 * 선택값을 외부에서 읽어야 하는 경우에만 [rememberPickerState]로 만들어 직접 넘기면 됩니다.
 * @param isInfinite true면 리스트 끝에서 처음으로 순환하는 무한 스크롤, false면
 * [items] 범위 안에서만 스크롤되는 유한 스크롤 (기본값: false)
 * @param startIndex 최초 선택 상태로 보여줄 [items]의 인덱스 (기본값: 0)
 * @param visibleItemsCount 화면에 동시에 보이는 아이템 개수. 홀수를 권장하며,
 * 정중앙 강조 색상 계산이 이 값을 기준으로 이루어집니다. (기본값: 5)
 * @param onItemHeightMeasured 아이템 한 줄의 실제 렌더링 높이가 측정될 때마다 호출되는
 * 콜백. 여러 [HapHapPicker]를 한 그룹으로 묶어 배경 하이라이트 높이를 맞춰야 할 때
 * 사용합니다 (예: [HapHapBottomSheet]).
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HapHapPicker(
    items: List<String>,
    modifier: Modifier = Modifier,
    state: PickerState = rememberPickerState(),
    isInfinite: Boolean = false,
    startIndex: Int = 0,
    visibleItemsCount: Int = 5,
    onItemHeightMeasured: (Dp) -> Unit = {},
){
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = if (isInfinite) Integer.MAX_VALUE else items.size + visibleItemsMiddle * 2
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex = if (isInfinite) {
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex
    } else {
        startIndex
    }

    fun getItem(index: Int): String {
        if (isInfinite) return items[index % items.size]
        val realIndex = index - visibleItemsMiddle
        return if (realIndex in items.indices) items[realIndex] else ""
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val textStyle = HapHapTheme.typography.body.sb18
    val maxItemWidth = remember(items, textStyle) {
        val maxWidthPx = items.maxOf { textMeasurer.measure(it, textStyle).size.width }
        with(density) { maxWidthPx.toDp() }
    }

    SideEffect { onItemHeightMeasured(itemHeightDp) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(maxItemWidth)
                .height(itemHeightDp * visibleItemsCount)
        ) {
            items(listScrollCount) { index ->
                val centerIndex = listState.firstVisibleItemIndex + visibleItemsMiddle +
                    if (itemHeightPixels.value > 0) {
                        (listState.firstVisibleItemScrollOffset.toFloat() / itemHeightPixels.value + 0.5f).toInt()
                    } else 0
                val distance = kotlin.math.abs(index - centerIndex)
                val itemColor = when (distance) {
                    0 -> HapHapTheme.colors.gray700
                    1 -> HapHapTheme.colors.gray400
                    else -> HapHapTheme.colors.gray200
                }
                Text(
                    text = getItem(index),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = textStyle,
                    color = itemColor,
                    modifier = Modifier
                        .onSizeChanged { size -> itemHeightPixels.value = size.height }
                        .padding(vertical = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

/** [PickerState]를 기억(remember)해서 반환합니다. */
@Composable
fun rememberPickerState() = remember { PickerState() }

/**
 * [HapHapPicker]의 현재 선택 상태를 담는 홀더.
 *
 * [rememberPickerState]로 생성해서 [HapHapPicker]의 `state` 파라미터로 넘기면,
 * 사용자가 휠을 스크롤할 때마다 [selectedItem]이 갱신됩니다.
 */
class PickerState {
    /** 현재 정중앙에 위치한, 선택된 것으로 취급되는 아이템 문자열 */
    var selectedItem by mutableStateOf("")
}

@Preview
@Composable
private fun HapHapPickerPreview() {
    HapHapTheme {
        HapHapPicker(
            items = (1..31).map { "${it}일" },
            startIndex = 10,
        )
    }
}
