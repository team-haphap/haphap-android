package com.haphap.app.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.haphap.app.core.designsystem.component.toast.LocalToastBottomInset

/**
 * 컴포넌트의 높이를 측정해 토스트의 하단 여백으로 등록합니다.
 *
 * 화면 전환 시 이전 화면이 새 화면의 값을 덮어쓰지 않도록,
 * ON_RESUME에서 값을 세팅하고 ON_PAUSE에서 0으로 초기화합니다.
 */
@Composable
fun Modifier.toastBottomInset(): Modifier {
    val density = LocalDensity.current
    val toastBottomInset = LocalToastBottomInset.current
    var currentMeasuredInset by remember { mutableStateOf(0.dp) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        toastBottomInset.value = currentMeasuredInset
    }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        toastBottomInset.value = 0.dp
    }

    return this.onSizeChanged { size ->
        val measuredDp = with(density) { size.height.toDp() }
        currentMeasuredInset = measuredDp
        toastBottomInset.value = measuredDp
    }
}