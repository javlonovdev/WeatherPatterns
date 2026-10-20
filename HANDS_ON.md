# Hands-on tasks

| Question | Implementation |
| --- | --- |
| 3 | data/api, data/model, data/repository; WeatherRepositoryTest |
| 4 | MainActivity, five manifest Activities, di/WeatherModule, ui/WeatherScreen |
| 6 | mvc/model, mvc/controller, mvc/view; comment above render() |
| 8 | mvp/WeatherContract, presenter, passive Activity; WeatherPresenterTest |
| 9 | WeatherPresenter.destroy() cancels the scope; MvpActivity.onDestroy() calls it |
| 11 | mvvm/viewmodel and mvvm/view; lifecycle collection and factory |
| 13 | mvi/model, mvi/viewmodel, mvi/view; factory and WeatherMviViewModelTest |
| 14 | Source inspection comparison below |

## Question 9: rotation evidence to record
The checked-in app includes the fix. The following is an experiment procedure and expected behavior, not an observed emulator transcript.

1. For the before-fix run ONLY, temporarily comment out `presenter.destroy()` inside MvpActivity.onDestroy().
2. Run MVP, filter Logcat with `tag:MVP`, tap Bukhara, and rotate within three seconds.
3. Record the old onCreate ID, its onDestroy ID, the new onCreate ID, and the ID in showWeather.
4. Expected before fix: showWeather is called on the old, destroyed Activity. The new screen says "Pick a city".
5. Restore `presenter.destroy()`, rerun and repeat.
6. Expected after fix: the old request is cancelled, so it does not call showWeather on the old Activity. The new screen still says "Pick a city": each new Activity owns a new Presenter and fresh status.
7. To retain state and a request through rotation, both need an owner that survives configuration changes, such as an Android ViewModel.

| Run | Old Activity ID | New Activity ID | showWeather recipient |
| --- | --- | --- | --- |
| Before fix | Record from Logcat | Record from Logcat | Record from Logcat |
| After fix | Record from Logcat | Record from Logcat | Expected: none for cancelled request |

Capture your real Logcat and app screen for submission. The cancellation unit test verifies that a pending presenter request produces no callback after destroy(), but it does not substitute for an emulator rotation experiment.

## Question 11: MVVM rotation
Tap Bukhara, then rotate within three seconds. Expected: Loading remains while the same ViewModel request continues, then "Bukhara: 27 C, Windy" appears. If already finished, the forecast remains. The Activity is recreated, the ViewModel and its StateFlow survive, and the new Activity collects the current state. MVI has the same configuration-change retention.

## Question 14: dependency inspection
These results describe the source in this repository. The import columns apply to the logic class, not the Activity.

| Logic class (pattern) | Imports android. | Imports androidx. | View references | Survives rotation |
| --- | --- | --- | --- | --- |
| WeatherController (MVC) | No | No | Controller and Model | Shared repository only; Activity, Model and Controller are recreated; pending work cancelled |
| WeatherPresenter (MVP) | No | No | Presenter | Shared repository only; Activity and Presenter are recreated; pending work cancelled |
| WeatherViewModel (MVVM) | No | Yes: lifecycle | ViewModel | ViewModel, StateFlow state and viewModelScope request |
| WeatherMviViewModel (MVI) | No | Yes: lifecycle | ViewModel | ViewModel, immutable state and viewModelScope request |

All four pattern packages contain android. and androidx. imports in their Activity files.
MVC logic can be tested on the local JVM with JUnit and a supplied Unconfined scope, without coroutines-test.
MVP success/error sequences can be tested the same way; the extra delayed cleanup test uses coroutines-test for virtual time.
MVVM local tests that use viewModelScope replace Dispatchers.Main using kotlinx-coroutines-test.
MVI local tests do the same and compare immutable WeatherState values.

To repeat in Android Studio, search each pattern directory for `import android.` and then `import androidx.`.

## Submission checks still requiring Android Studio
- Run all unit tests and save the actual result.
- Build and launch the app; check every pattern and all four cities.
- Verify Android Back returns to the launcher.
- Record both MVP rotation experiments and the MVVM rotation experiment.
- Capture screenshots if required by your teacher.
