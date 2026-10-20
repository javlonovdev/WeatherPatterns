package uz.ttpu.weatherpatterns

import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Test
import uz.ttpu.weatherpatterns.data.api.*
import uz.ttpu.weatherpatterns.data.model.Weather
import uz.ttpu.weatherpatterns.data.repository.WeatherRepository

import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.After
import uz.ttpu.weatherpatterns.mvp.WeatherContract
import uz.ttpu.weatherpatterns.mvp.presenter.WeatherPresenter
class FakeView : WeatherContract.View {
 val events = mutableListOf<String>()
 override fun showLoading() { events += "loading" }
 override fun showWeather(weather: Weather) { events += "weather:${weather.city}" }
 override fun showError(message: String) { events += "error:$message" }
}
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherPresenterTest {
 private val view = FakeView()
 private val presenter = WeatherPresenter(view, WeatherRepository(FakeWeatherApi(0)), CoroutineScope(Dispatchers.Unconfined))
 @Test fun known_city_shows_loading_then_weather() {
 presenter.onCitySelected("Tashkent")
 assertEquals(listOf("loading", "weather:Tashkent"), view.events)
 }
 @Test fun unknown_city_shows_loading_then_error() {
 presenter.onCitySelected("Atlantis")
 assertEquals(listOf("loading", "error:No forecast for Atlantis"), view.events)
 }
 @Test fun two_requests_in_a_row_show_two_loading_events() {
 presenter.onCitySelected("Tashkent"); presenter.onCitySelected("Bukhara")
 assertEquals(listOf("loading", "weather:Tashkent", "loading", "weather:Bukhara"), view.events)
 }
 @Test fun destroy_cancels_pending_callback() = runTest {
 val delayedPresenter = WeatherPresenter(view, WeatherRepository(FakeWeatherApi(3000)), backgroundScope)
 delayedPresenter.onCitySelected("Bukhara")
 runCurrent()
 delayedPresenter.destroy()
 advanceUntilIdle()
 assertEquals(listOf("loading"), view.events)
 }
}