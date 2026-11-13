package com.haphap.app.core.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.haphap.app.core.designsystem.component.toast.LocalToastBottomInset

/**
 * 리플 효과 없이 클릭 가능하게 만드는 Modifier
 *
 * Material3의 기본 리플 애니메이션을 제거합니다.
 *
 * @param onClick 클릭 시 실행될 콜백
 */
fun Modifier.noRippleClickable(
    onClick: () -> Unit,
    isEnabled: Boolean = true,
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
        enabled = isEnabled,
    )
}

/**
 * 컴포넌트 안쪽에 둥근 점선 테두리를 그리는 Modifier
 *
 * 선은 레이아웃 공간을 차지하지 않으며, 체인에서 앞에 있는 Modifier의 그리기 결과 위에 그려집니다.
 * 따라서 background보다 뒤에 적용해야 테두리가 가려지지 않습니다.
 *
 * @param color 점선 색상
 * @param cornerRadius 모서리 반경
 * @param strokeWidth 선 두께
 * @param dashLength 점선 한 칸의 길이
 * @param gapLength 점선 사이 간격
 */
fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp,
    strokeWidth: Dp = 1.dp,
    dashLength: Dp = 3.dp,
    gapLength: Dp = 3.dp,
): Modifier = drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    val inset = strokeWidthPx / 2
    val radius = cornerRadius.toPx() - inset

    drawRoundRect(
        color = color,
        topLeft = Offset(inset, inset),
        size = Size(size.width - strokeWidthPx, size.height - strokeWidthPx),
        cornerRadius = CornerRadius(radius),
        style = Stroke(
            width = strokeWidthPx,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(dashLength.toPx(), gapLength.toPx()),
            ),
        ),
    )
}

/**
 * 컴포넌트의 높이를 측정해 토스트의 하단 여백으로 등록합니다.
 *
 * 화면이 RESUMED 상태일 때만 측정된 높이를 토스트 하단 여백으로 반영합니다.
 * 화면이 PAUSE되면 기존 하단 여백을 초기화합니다.
 */
@Composable
fun Modifier.toastBottomInset(): Modifier {
    val density = LocalDensity.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val toastBottomInset = LocalToastBottomInset.current
    var currentMeasuredInset by rememberSaveable { mutableFloatStateOf(0f) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        toastBottomInset.value = currentMeasuredInset.dp
    }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        toastBottomInset.value = 0.dp
    }

    return this.onSizeChanged { size ->
        val measuredDp = with(density) { size.height.toDp() }
        currentMeasuredInset = measuredDp.value

        if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
            toastBottomInset.value = measuredDp
        }
    }
}