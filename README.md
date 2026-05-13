# WeatherSnap 🌤️

> Live weather reports with photographic evidence — built for Android with modern Jetpack stack.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.1.0 |
| UI | Jetpack Compose (BOM 2024.10.00) + Material 3 |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt 2.51.1 |
| Networking | Retrofit 2.9.0 + OkHttp 4.12.0 + Gson |
| Database | Room 2.6.1 |
| Camera | CameraX 1.3.1 |
| Image Loading | Coil 2.6.0 |
| Async | Coroutines + StateFlow |
| Navigation | Navigation Compose 2.7.7 |
| Build | KSP 2.1.0-1.0.29 |

---

## Features

- **City search** with debounced autocomplete and in-memory suggestion cache
- **Live weather** — temperature, condition, humidity, wind speed, pressure via Open-Meteo (no API key)
- **Custom CameraX screen** — no device camera intent, full live preview with capture
- **Image compression** — JPEG at 60% quality with original vs. compressed size display
- **Room persistence** — reports survive app restarts, fully offline after save
- **Soft delete / Trash** — move reports to trash, restore or permanently delete
- **Edit reports** — update notes or recapture photo on any saved report

---

## Screens

```
WeatherScreen → WeatherDetailsScreen → CreateReportScreen → CameraScreen
                                                         ↓
                                               SavedReportsScreen
                                               TrashScreen
                                               EditReportScreen
```

---

## Prerequisites

- Android Studio Iguana (2023.2.1) or newer
- JDK 17
- Android SDK 24+ (minSdk 24, targetSdk 34)
- Physical device or emulator with camera support (API 24+)

---

## Setup & Run

```bash
# 1. Clone
git clone https://github.com/maniktyagi04/WeatherSnap.git
cd WeatherSnap

# 2. Open in Android Studio → File > Open → select the cloned folder
#    Let Gradle sync complete automatically

# 3. Run on device/emulator
./gradlew installDebug
```

Or just hit **Run ▶** in Android Studio after sync.

> Camera permission is requested at runtime on first launch. Grant it to enable report creation.

---

## Build Commands

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Clean
./gradlew clean

# Clean + rebuild
./gradlew clean assembleDebug
```

---

## Testing

```bash
# Unit tests (ViewModel + Repository)
./gradlew test

# Instrumented tests (Room DAO + Compose UI)
./gradlew connectedAndroidTest

# All tests with report
./gradlew test connectedAndroidTest --continue
```

Test reports output to:
- Unit: `app/build/reports/tests/testDebugUnitTest/index.html`
- Instrumented: `app/build/reports/androidTests/connected/index.html`

**Test coverage:**
- `WeatherViewModelTest` — search query flow, city selection, StateFlow assertions (MockK + Turbine)
- `WeatherRepositoryImplTest` — success/failure paths for geocoding (MockK + coroutines-test)
- `ReportDaoTest` — insert, query active/trash, soft delete, restore (in-memory Room)
- `WeatherScreenTest` — Compose UI state rendering

---

## API Reference

No API key required. Uses [Open-Meteo](https://open-meteo.com/).

| Endpoint | Purpose |
|---|---|
| `geocoding-api.open-meteo.com/v1/search` | City autocomplete |
| `api.open-meteo.com/v1/forecast` | Current weather by lat/lon |

Network logging is **debug-only** (`BuildConfig.DEBUG` gate on `HttpLoggingInterceptor`).

---

## Project Structure

```
com.manik.weathersnap
├── data
│   ├── local/          # Room DB, DAO, ReportEntity
│   ├── remote/         # Retrofit services, DTOs, mappers
│   └── repository/     # WeatherRepositoryImpl
├── domain
│   ├── model/          # City, Weather
│   └── repository/     # WeatherRepository interface
├── ui
│   ├── camera/         # CameraX screen + CameraManager
│   ├── report/         # CreateReportScreen + ViewModel
│   ├── savedreports/   # SavedReportsScreen + ViewModel
│   ├── edit/           # EditReportScreen + ViewModel
│   ├── trash/          # TrashScreen + ViewModel
│   ├── weather/        # WeatherScreen + WeatherDetailsScreen + ViewModel
│   ├── common/         # Shared composables
│   └── theme/          # Material 3 color, type, theme
├── navigation/         # NavGraph, Routes
└── utils/              # ImageCompressor, DateUtils
```

---

## Notes

- `fallbackToDestructiveMigration()` is enabled — reinstalling after a DB schema change will wipe local data.
- Compressed images are stored in `externalCacheDir` and may be cleared by the system under storage pressure.
- The app targets API 34; camera permission handling follows the runtime permission model (no legacy `READ_EXTERNAL_STORAGE`).
