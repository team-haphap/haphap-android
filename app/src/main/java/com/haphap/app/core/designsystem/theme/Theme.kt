package com.haphap.app.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalHapHapColors = staticCompositionLocalOf { defaultHapHapColors }
private val LocalHapHapTypography = staticCompositionLocalOf { defaultHapHapTypography }

object HapHapTheme {
    val colors: HapHapColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHapHapColors.current

    val typography: HapHapTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHapHapTypography.current
}

@Composable
fun HapHapTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalHapHapColors provides defaultHapHapColors,
        LocalHapHapTypography provides defaultHapHapTypography,
    ) {
        MaterialTheme(content = content)
    }
}