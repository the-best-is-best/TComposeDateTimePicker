# Compose Date Time Picker

<p align="center">
  <strong>A Compose Multiplatform library for elegant and customizable date, time, and date-time pickers.</strong>
</p>

<div align="center">
  <a href="https://opensource.org/licenses/Apache-2.0">
    <img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/>
  </a>
  <a href="https://android-arsenal.com/api?level=21">
    <img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/>
  </a>
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android" alt="Badge Android"/>
  <img src="https://img.shields.io/badge/Platform-iOS%20%2F%20macOS-lightgrey.svg?logo=apple" alt="Badge iOS"/>
  <img src="https://img.shields.io/badge/Platform-JVM-8A2BE2.svg?logo=openjdk" alt="Badge JVM"/>
  <img src="https://img.shields.io/badge/Platform-WASM%20%2F%20JS-yellow.svg?logo=javascript" alt="Badge JS"/>
  <a href="https://github.com/the-best-is-best/">
    <img alt="Profile" src="https://img.shields.io/badge/github-%23181717.svg?&style=for-the-badge&logo=github&logoColor=white" height="20"/>
  </a>
</div>

---

## 📌 Overview

**Compose Date Time Picker** is a powerful and flexible library for **Compose Multiplatform** that
provides easy-to-use and customizable date, time, and date-time pickers. It is designed to work
seamlessly across multiple platforms, including **Android**, **iOS**, **macOS**, **JVM**, and *
*JS/WASM**.

The library also includes a rich set of extensions for `kotlinx.datetime` to simplify date and time
manipulation, formatting, and conversion.

---

## ✨ Features

- **Cross-Platform Support**: Works on Android, iOS, macOS, JVM, and JS/WASM.
- **Customizable Pickers**:
  - **Date Picker**: `TKDatePicker`
  - **Time Picker**: `TKTimePicker`
  - **Date-Time Picker**: `TKDateTimePicker`
- **Adaptive UI**: Automatically adapts to different screen sizes and platforms using
  the [calf-ui](https://central.sonatype.com/artifact/com.mohamedrejeb.calf/calf-ui) library.
- **Rich Extensions** for `kotlinx.datetime`, including:
  - Date/time formatting.
  - Conversion between `LocalDate`, `LocalDateTime`, and epoch milliseconds.
  - Comparison and truncation utilities.
- **Lightweight and Easy to Use**.

---

## Download

[![Maven Central](https://img.shields.io/maven-central/v/io.github.the-best-is-best/compose_date_time_picker)](https://central.sonatype.com/artifact/io.github.the-best-is-best/compose_date_time_picker)

## 📦 Installation

Add the dependency to your `build.gradle.kts` file:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
  implementation("io.github.the-best-is-best:compose_date_time_picker:3.2.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:<version>") // Required for date/time manipulation
}
```

---

## 🚀 Usage

### 1. Date Picker

```kotlin
TKDatePicker(
    config = ConfigDatePicker(
        label = { Text("Select Date") }
    ),
    useAdaptive = false,
    isDialogOpen = { /* Handle dialog state */ },
    onDateSelected = { selectedDate ->
        println("Selected date: $selectedDate")
    }
)
```

### 2. Time Picker

```kotlin
TKTimePicker(
    config = ConfigTimePicker(
        label = { Text("Select Time") }
    ),
    useAdaptive = false,
    isDialogOpen = { /* Handle dialog state */ },
    onTimeSelected = { selectedTime ->
        println("Selected time: $selectedTime")
    }
)
```

### 3. Date-Time Picker

```kotlin
TKDateTimePicker(
    textFieldType = TextFieldType.Filled,
    config = ConfigDateTimePicker(
        label = { Text("Select Date and Time") }
    ),
    useAdaptive = false,
    isDialogOpen = { /* Handle dialog state */ },
    onDateTimeSelected = { selectedDateTime ->
        println("Selected date and time: ${selectedDateTime?.toIsoStringWithOffset()}")
    }
)
```

### 4. Custom Integration

```kotlin
TKDateTimePicker(
    textFieldType = TextFieldType.Custom { modifier -> },
    config = ConfigDateTimePicker(
        label = { Text("Select Date and Time") }
    ),
    useAdaptive = false,
    isDialogOpen = { /* Handle dialog state */ },
    onDateTimeSelected = { selectedDateTime ->
        println("Selected date and time: ${selectedDateTime?.toIsoStringWithOffset()}")
    }
)
```

---

## 🛠️ Extensions for kotlinx.datetime

### Date Extensions

```kotlin
val today = LocalDate.now() // Get current date
val formattedDate = today.formatLocalDate() // Format as "dd/MM/yyyy"
val epochMillis = today.toEpochMillis() // Convert to epoch milliseconds
val isLeapYear = today.isLeapYear() // Check if the year is a leap year
```

### DateTime Extensions

```kotlin
val now = LocalDateTime.now()
val formattedDateTime = now.formatLocalDateTime(use24HourFormat = false)
val truncatedDateTime = now.truncatedTo(DurationUnit.HOURS)
```

### Time Extensions

```kotlin
val currentTime = LocalTime.now()
val formattedTime = currentTime.formatLocalTime(withoutSeconds = true)
```

---

## 🌟 Adaptive UI Support

The `useAdaptive` parameter enables adaptive UI support, ensuring that the pickers look great on all
platforms and screen sizes. This feature relies on
the [calf-ui](https://central.sonatype.com/artifact/com.mohamedrejeb.calf/calf-ui) library.

---

## 📄 License

This library is open-source and licensed under the **MIT License**.

```
MIT License

Copyright (c) 2023 the-best-is-best

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.
```

---

## 🙏 Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open
an issue or submit a pull request.
