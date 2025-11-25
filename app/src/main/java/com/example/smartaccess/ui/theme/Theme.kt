package com.example.smartaccess.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@Composable
fun SmartAccessTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = androidx.compose.material.lightColors(
            primary = md_theme_primary,
            onPrimary = md_theme_onPrimary,
            background = md_theme_background
        ),
        typography = Typography(),
        shapes = androidx.compose.material.Shapes(),
        content = content
    )
}
