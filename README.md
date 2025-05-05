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

## Setup
Import the reactive-mulitplatform-charts with Gradle

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        // point to maven snapshots as currently no releases are available
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}
```

```kotlin
// build.gradle.kts
implementation("io.github.josuablejeru:reactive-multiplatform-charts:0.1.0-SNAPSHOT")
```

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

# Releasing

- Set the `ANDROID_HOME` env variable.
- Follow the setup guid from [gradle-maven-publish-plugin](https://vanniktech.github.io/gradle-maven-publish-plugin/central/#secrets)
- execute `:library:publishAllPublicationsToMavenCentralRepository --no-configuration-cache`
