# Reactive Multiplatform Charts

Reactive Multiplatform Charts is a lightweight Kotlin Multiplatform library designed for real-time, reactive, and live-updating charts.

Built with Kotlin Coroutines Flows and Compose Multiplatform, it offers:
- 🔥 Multiple data streams (SharedFlow, StateFlow, Hot Flows)
- 📊 Dynamic Y-axis scaling
- ➡️ Automatic X-axis scrolling for live dashboards
- 💻 Multiplatform ready: Android, Desktop, iOS (Compose Multiplatform)

Key Concepts:
- Unopinionated: Bring your own data models, we just plot!
- Reactive-first: Built around Flows for hot live data.

## Usage
- [LineChart](#linechart)


## LineChart
![Line chart](/assets/LineChartDemo.png)

DataSeries takes in a Flow of any type like a `MutableSharedFlow`
```kotlin
val series = listOf(
    DataSeries(
        flow = flow1,
        color = Color.Blue,
        xSelector = { it.x },
        ySelector = { it.y }
    ),
)
```


```kotlin
LineChart(
    series = series,
    modifier = Modifier.fillMaxSize(),
    visibleXRange = 600f
)
```
