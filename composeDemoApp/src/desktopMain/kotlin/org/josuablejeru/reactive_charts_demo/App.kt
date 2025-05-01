package org.josuablejeru.reactive_charts_demo

import com.josuablejeru.reactive_multiplatform_charts.core.LineChart
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.josuablejeru.reactive_multiplatform_charts.charts.DataSeries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.sin


data class SensorReading(val x: Float, val y: Float)

fun liveDataFlow(scope: CoroutineScope, invert: Boolean, offset: Float): SharedFlow<List<SensorReading>> {
    val sharedFlow = MutableSharedFlow<List<SensorReading>>(replay = 1)
    val points = mutableListOf<SensorReading>()

    scope.launch(Dispatchers.Default) {
        var currentX = 0f
        while (true) {
            delay(50) // faster updates
            currentX += 5f

            if (invert) {
                points.add(
                    SensorReading(
                        x = currentX + offset,
                        y = -(sin(currentX * 0.05f + offset) * 100f) + 150f // Sinus wave
                    )
                )
            } else {
                points.add(
                    SensorReading(
                        x = currentX + offset,
                        y = (sin(currentX * 0.05f + offset) * 100f) + 150f // Sinus wave
                    )
                )
            }

            sharedFlow.emit(points.toList())
        }
    }

    return sharedFlow
}

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    val flow1 = remember { liveDataFlow(scope, invert = false, offset = 0f) }
    val flow2 = remember { liveDataFlow(scope, invert = true, offset = 0f) }
    val flow3 = remember { liveDataFlow(scope, invert = false, offset = 7f) }
    val flow4 = remember { liveDataFlow(scope, invert = true, offset = 14f) }

    val series = listOf(
        DataSeries(
            flow = flow1,
            color = Color.Blue,
            xSelector = { it.x },
            ySelector = { it.y }
        ),
        DataSeries(
            flow = flow2,
            color = Color.Green,
            xSelector = { it.x },
            ySelector = { it.y }
        ),
        DataSeries(
            flow = flow3,
            color = Color.Yellow,
            xSelector = { it.x },
            ySelector = { it.y }
        ),
        DataSeries(
            flow = flow4,
            color = Color.Red,
            xSelector = { it.x },
            ySelector = { it.y }
        ),
    )
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LineChart(
                series = series,
                modifier = Modifier.fillMaxSize(),
                visibleXRange = 600f
            )
        }
    }
}
