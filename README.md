# WeatherSnap 🌤️📸

WeatherSnap is a professional-grade Android application that allows users to capture and persist weather reports with real-time data and photographic evidence. Built with modern Android development practices, it features a sleek, animated interface, robust offline persistence, and optimized image processing.

## ✨ Key Features

- **Real-time Weather**: Search for any city globally to fetch live weather metrics (Temperature, Humidity, Wind Speed, Pressure).
- **Photo Reports**: Capture moments using an integrated CameraX interface.
- **Image Optimization**: Automatic JPEG compression (60% quality) with real-time stats (Raw vs. Optimized size) to save storage.
- **Offline Persistence**: Full Room database integration to save and browse your weather reports offline.
- **Premium UI/UX**:
    - Material 3 Design System with a weather-inspired color palette.
    - Smooth animations using `AnimatedVisibility` and `Crossfade`.
    - Liquid transitions between screens.
    - Dark Mode support with high-contrast elements.
- **Local Search Cache**: In-memory caching for city suggestions to minimize network latency.

## 🛠️ Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture**: MVVM + Clean Architecture principles
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/)
- **Camera**: [CameraX](https://developer.android.com/training/camerax)
- **Concurrency**: Kotlin Coroutines & Flow

## 📂 Project Structure

```text
com.manik.weathersnap
├── data
│   ├── local      # Room Database, DAOs, Entities
│   ├── remote     # Retrofit Services, DTOs, API Interfaces
│   └── repository # Implementation of domain repositories
├── domain
│   ├── model      # Clean domain models
│   └── repository # Repository interfaces
├── ui
│   ├── camera     # CameraX implementation
│   ├── report     # Report creation and optimization logic
│   ├── savedreports # List of persisted reports
│   ├── weather    # Search and weather dashboard
│   ├── theme      # Material 3 Design System
│   └── common     # Shared UI components
├── utils          # Extensions, Image Compression logic
└── navigation     # Type-safe navigation graph
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Iguana (2023.2.1) or higher
- JDK 17
- Android SDK 24+

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/maniktyagi04/WeatherSnap.git
   ```
2. Open the project in Android Studio.
3. Sync Project with Gradle Files.
4. Run the app on an emulator or physical device.

## 🧪 Testing

The project includes a robust testing suite:
- **Unit Tests**: Repository and ViewModel logic tested with MockK and Turbine.
- **Database Tests**: Instrumented tests using an in-memory Room database.
- **UI Tests**: Compose testing for screen states and transitions.

Run tests using the following commands:
```bash
./gradlew test        # Run Unit Tests
./gradlew connectedAndroidTest # Run Instrumented Tests
```

## 🏗️ Commands

- **Build Project**: `./gradlew build`
- **Clean Project**: `./gradlew clean`
- **Install Debug APK**: `./gradlew installDebug`


---
*Developed as a high-quality technical internship assignment.*
