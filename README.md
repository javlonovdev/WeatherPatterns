# WeatherPatterns
Android architecture practice: Kotlin, Jetpack Compose, Material 3, package `uz.ttpu.weatherpatterns`, minimum API 24.

Only hands-on Questions **3, 4, 6, 8, 9, 11, 13 and 14** are covered. No theoretical answers are included.

## Open and run
1. Extract the archive and open this folder in Android Studio.
2. Use JDK 17 for Gradle. Install Android SDK Platform 35 and Build Tools 35.0.0 when prompted.
3. Let Gradle sync. Run the app on an API 24+ emulator or phone.
4. The launcher opens MVC, MVP, MVVM and MVI. Android Back returns to the launcher.
5. Each screen has Tashkent, Samarkand, Bukhara and Atlantis. Requests take three seconds.

Expected forecasts: Tashkent 24 C Sunny; Samarkand 21 C Cloudy; Bukhara 27 C Windy.
Atlantis displays `Error: No forecast for Atlantis`.

## Checks
Windows: `gradlew.bat testDebugUnitTest assembleDebug lintDebug`
macOS/Linux: `chmod +x gradlew && ./gradlew testDebugUnitTest assembleDebug lintDebug`

Fourteen unit tests cover the repository, MVC controller, MVP presenter and cleanup, MVVM state, MVI state and display priority.
GitHub Actions runs unit tests, APK assembly and lint and uploads reports and the debug APK.

## Practical evidence
See [HANDS_ON.md](HANDS_ON.md) for the task map, rotation experiment and dependency comparison.
Source and test files are implemented. An Android build and emulator run could not be completed in the preparation environment: Android SDK was unavailable and external dependency downloads were blocked. No passing Android build or observed Logcat values are claimed.

## GitHub
This folder contains no credentials, SDK paths or build outputs. Upload its contents as a repository, preserving the `.github` directory.
Use a private repository unless your course requires public visibility. The workflow runs after a push.

## Scope
The implementation follows the supplied practice's coroutine behavior. It does not serialize overlapping city requests; rapid clicks may finish in completion order. Rotation retention in MVVM/MVI lasts for configuration changes, not process death.
