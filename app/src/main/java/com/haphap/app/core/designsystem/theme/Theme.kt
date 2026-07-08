package com.haphap.app.core.designsystem.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LocalHapHapColors = staticCompositionLocalOf { defaultHapHapColors }
private val LocalHapHapTypography = staticCompositionLocalOf { defaultHapHapTypography }

private val HapHapColorScheme = lightColorScheme(
    primary = Primary500,
    background = White,
    surface = White,
)

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
        MaterialTheme(
            colorScheme = HapHapColorScheme,
            content = content,
        )
    }
}