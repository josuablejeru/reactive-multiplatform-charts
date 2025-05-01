package org.josuablejeru.reactive_charts_demo

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.josuablejeru.reactive_multiplatform_charts.models.DataPoint

@Composable
@Preview
fun App() {
    MaterialTheme {
        val p1 = DataPoint(1f, 2f)
        Text(text = "Hello ReactiveCharts! $p1")
    }
}
