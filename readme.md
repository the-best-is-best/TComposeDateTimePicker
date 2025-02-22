<h1 align="center">Compose Date Time Picker</h1><br>

## 📌 Overview

Compose Date Picker is library for Compose multi-platform to make date or time picker and date time
picker.

<div align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=21" rel="nofollow">
    <img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
</a>
<img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android" />
  <img src="https://img.shields.io/badge/Platform-iOS%20%2F%20macOS-lightgrey.svg?logo=apple" alt="Badge iOS" />
  <img src="https://img.shields.io/badge/Platform-JVM-8A2BE2.svg?logo=openjdk" alt="Badge JVM" />
    <img src="https://img.shields.io/badge/Platform-WASM%20%2F%20JS-yellow.svg?logo=javascript" alt="Badge JS" />
<a href="https://github.com/the-best-is-best/"><img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/></a>

</div>

## Download

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/compose_date_time_picker)](https://central.sonatype.com/artifact/io.github.the-best-is-best/compose_date_time_picker)

Compose Date Time Picker is available on `mavenCentral()`.

```kotlin
implementation("io.github.the-best-is-best:compose_date_time_picker:3.1.1")
```

## How to use

### First

```gradle.kts
   implementation("org.jetbrains.kotlinx:kotlinx-datetime:<version>")
```

### Second

```kotlin
 TKDateTimePicker(

    textFieldType = TextFieldType.Filled,
    config = ConfigDateTimePicker(
        label = {
            Text("Select date and time")
        }

    ),
    isDialogOpen = {

    },
    onDateTimeSelected = {
        println("date time selected is ${it?.toIsoStringWithOffset()}")
    }
)

TKDatePicker(
    config = ConfigDatePicker(
        label = {
            Text("Select Date")
        }

    ),
    isDialogOpen = {

    },
    onDateSelected = {
        println("date time selected is $it")
    }
)

TKTimePicker(
    config = ConfigTimePicker(
        label = {
            Text("Time")
        }
    ),
    isDialogOpen = {

    },
    onTimeSelected = {
        println("date time selected is $it")
    }
)
```
