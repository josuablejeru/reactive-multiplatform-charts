package io.github.josuablejeru.reactive_multiplatform_charts.core

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.Flow

/**
 * Represents a continues Data stream
 */
data class DataSeries<T>(
    val flow: Flow<T>,
    val color: Color,
    val xSelector: (T) -> Float,
    val ySelector: (T) -> Float
)
