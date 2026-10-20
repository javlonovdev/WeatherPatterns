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
import uz.ttpu.weatherpatterns.mvi.model.*
import uz.ttpu.weatherpatterns.mvi.viewmodel.WeatherMviViewModel
import uz.ttpu.weatherpatterns.mvi.view.toStatusText
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherMviViewModelTest {
 @Before fun setUp() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
 @After fun tearDown() { Dispatchers.resetMain() }
 private fun newViewModel() = WeatherMviViewModel(WeatherRepository(FakeWeatherApi(0)))
 @Test fun selecting_a_known_city_ends_with_weather_and_no_loading() {
 val vm = newViewModel(); vm.onIntent(WeatherIntent.SelectCity("Bukhara"))
 assertEquals(WeatherState(weather = Weather("Bukhara", 27, "Windy")), vm.state.value)
 }
 @Test fun selecting_an_unknown_city_ends_with_error() {
 val vm = newViewModel(); vm.onIntent(WeatherIntent.SelectCity("Atlantis"))
 assertEquals(WeatherState(error = "No forecast for Atlantis"), vm.state.value)
 }
 @Test fun dismissing_the_error_clears_it_and_keeps_the_old_weather() {
 val vm = newViewModel()
 vm.onIntent(WeatherIntent.SelectCity("Tashkent"))
 val previousWeather = vm.state.value.weather
 vm.onIntent(WeatherIntent.SelectCity("Atlantis"))
 assertEquals(previousWeather, vm.state.value.weather)
 assertNotNull(vm.state.value.error)
 vm.onIntent(WeatherIntent.DismissError)
 assertEquals(WeatherState(weather = previousWeather), vm.state.value)
 }
 @Test fun loading_has_priority_over_old_weather_and_error() {
 assertEquals("Loading\u2026", WeatherState(true, Weather("Bukhara", 27, "Windy"), "Old error").toStatusText())
 }
 @Test fun error_has_priority_over_retained_weather() {
 assertEquals("Error: No forecast for Atlantis", WeatherState(weather = Weather("Tashkent", 24, "Sunny"), error = "No forecast for Atlantis").toStatusText())
 }
}