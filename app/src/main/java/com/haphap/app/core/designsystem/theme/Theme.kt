package com.haphap.app.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalHaphapColors = staticCompositionLocalOf { defaultHaphapColors }
private val LocalHaphapTypography = staticCompositionLocalOf { defaultHaphapTypography }

object HapHapTheme {
    val colors: HaphapColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHaphapColors.current

    val typography: HaphapTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHaphapTypography.current
}

@Composable
fun HapHapTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalHaphapColors provides defaultHaphapColors,
        LocalHaphapTypography provides defaultHaphapTypography,
    ) {
        MaterialTheme(content = content)
    }
}