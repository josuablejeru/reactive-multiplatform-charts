package org.josuablejeru.reactive_charts_demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "reactive-charts-demo",
    ) {
        App()
    }
}