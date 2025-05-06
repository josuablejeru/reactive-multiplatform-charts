package io.github.josuablejeru.reactive_multiplatform_charts.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.josuablejeru.reactive_multiplatform_charts.core.DataSeries
import kotlinx.coroutines.launch

/**
 * A simple resizable LineChart with the ability to have more than one
 * lines drawn. Must be used with a DataSeries object
 *
 * Example Usage:
 *
 * val series = listOf(
 *         DataSeries(
 *             flow = flow1,
 *             color = Color.Blue,
 *             xSelector = { it.x },
 *             ySelector = { it.y }
 *         ),
 *         DataSeries(
 *             flow = flow2,
 *             color = Color.Green,
 *             xSelector = { it.x },
 *             ySelector = { it.y }
 *     ),
 * )
 * LineChart(
 *     series = series,
 *     modifier = Modifier.fillMaxSize(),
 *     visibleXRange = 600f
 * )
 */
@Composable
fun <T> LineChart(
    series: List<DataSeries<T>>,
    modifier: Modifier = Modifier,
    visibleXRange: Float = 500f,
) {
    val dataList = remember { series.map { mutableStateListOf<T>() } }

    LaunchedEffect(series) {
        series.forEachIndexed { index, s ->
            launch {
                s.flow.collect { point ->
                    dataList[index].add(point)
                    // Optional: Trim old points to avoid memory issues
                    if (dataList[index].size > 1000) {
                        dataList[index].removeAt(0)
                    }
                }
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        if (dataList.isEmpty()) return@Canvas

        series.forEachIndexed { index, s ->
            val data = dataList.getOrNull(index) ?: return@forEachIndexed
            if (data.size < 2) return@forEachIndexed

            val xValues = data.map(s.xSelector)
            val yValues = data.map(s.ySelector)

            val currentMaxX = xValues.maxOrNull() ?: 0f
            val minVisibleX = currentMaxX - visibleXRange
            val maxVisibleX = currentMaxX

            val minY = yValues.minOrNull() ?: 0f
            val maxY = yValues.maxOrNull() ?: 1f

            fun normalizeX(x: Float): Float {
                if (maxVisibleX == minVisibleX) return size.width / 2f
                return ((x - minVisibleX) / (maxVisibleX - minVisibleX)) * size.width
            }

            fun normalizeY(y: Float): Float {
                if (maxY == minY) return size.height / 2f
                return size.height - ((y - minY) / (maxY - minY)) * size.height
            }

            val visiblePoints = data.filter { s.xSelector(it) >= minVisibleX }
            val points = visiblePoints.map {
                Offset(normalizeX(s.xSelector(it)), normalizeY(s.ySelector(it)))
            }

            if (points.size < 2) return@forEachIndexed

            val path = Path().apply {
                moveTo(points.first().x, points.first().y)
                for (i in 1 until points.size) {
                    val prev = points[i - 1]
                    val curr = points[i]
                    quadraticTo(
                        prev.x,
                        prev.y,
                        (prev.x + curr.x) / 2,
                        (prev.y + curr.y) / 2
                    )
                }
            }

            drawPath(
                path = path,
                color = s.color,
                style = Stroke(width = 4f)
            )
        }
    }
}
