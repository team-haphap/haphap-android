package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun RegisterStepScaffold(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    progress: Int? = null,
    totalSteps: Int = 3,
    isTopBarTextVisible: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white),
    ) {
        RegisterTopBar(
            onBackClick = onBackClick,
            isText = isTopBarTextVisible,
        )

        if (progress != null) {
            RegisterProgressBar(progress = progress, totalSteps = totalSteps)
        }

        content()
    }
}