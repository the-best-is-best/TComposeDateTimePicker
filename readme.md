<h1 align="center">Compose Date Picker</h1><br>

<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=21" rel="nofollow"><img alt="API" src="https://camo.githubusercontent.com/0eda703da08220e08354f624a3fc0023f10416a302565c69c3759bf6e0800d40/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4150492d32312532422d627269676874677265656e2e7376673f7374796c653d666c6174" data-canonical-src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" style="max-width: 100%;"></a>
<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>
<a href="https://central.sonatype.com/search?q=io.github.the-best-is-best&smo=true"><img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.the-best-is-best/TComposeDatePicker"/></a>
</div>

Compose Date Picker is a Jetpack Compose library for Android to make date or time picker and date time picker.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/TComposeDateTimePicker)](https://central.sonatype.com/artifact/io.github.the-best-is-best/TComposeDateTimePicker)

Compose Searchable Dropdown is available on `mavenCentral()`.

```kotlin
implementation("io.github.the-best-is-best:TComposeDateTimePicker:1.0.0")
```


## How to use

### `First`

```gradle.kts
   
dependencies {
...
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.9.0")
...
```

### `Second if minSdk < 26`

```gradle.kts
    
dependencies {
...
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.4")
...

android {
    ...
    defaultConfig {
        ...
       compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
        ...
    }
        ...
    }
  
    ...
}
```

### Date Picker

```kotlin
val date = remember { mutableStateOf(LocalDate.now()) }
TDatePicker.ShowDatePicker(
    config = ConfigDatePicker(
        allowedDateValidator = {activeDateInFutureOnly(it, false)},
        yearRange = IntRange(LocalDate.now().year, LocalDate.now().year+100),
    ),
    onDateSelected = {
        dateTime = LocalDateTime.of(it, dateTime.toLocalTime())
        Log.d("MainActivity", "Selected date: $dateTime")
    }
)

 ```

### Time Picker

    ```kotlin
    val time = remember { mutableStateOf(LocalTime.now()) }
    TTimePicker.ShowTimePicker(
        config = ConfigTimePicker(
            is24HourClock = true
        ),
        onTimeSelected = {
            dateTime = LocalDateTime.of(dateTime.toLocalDate(), it)
            Log.d("MainActivity", "Selected time: $dateTime")
        }
    )
    ```

### Date Time Picker

    ```kotlin
    val dateTime = remember { mutableStateOf(LocalDateTime.now()) }
    TDateTimePicker.ShowDateTimePicker(
        config = ConfigDateTimePicker(
            allowedDateValidator = {activeDateInFutureOnly(it, false)},
            yearRange = IntRange(LocalDate.now().year, LocalDate.now().year+100),
            is24HourClock = true
        ),
        onDateTimeSelected = {
            dateTime.value = it
            Log.d("MainActivity", "Selected date time: $it")
        }
    )
    ```