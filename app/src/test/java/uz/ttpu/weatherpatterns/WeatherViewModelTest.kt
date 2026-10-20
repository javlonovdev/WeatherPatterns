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
import uz.ttpu.weatherpatterns.mvvm.viewmodel.*
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {
 @Before fun setUp() { Dispatchers.setMain(UnconfinedTestDispatcher()) }
 @After fun tearDown() { Dispatchers.resetMain() }
 @Test fun known_and_unknown_cities_publish_terminal_states() {
 val vm = WeatherViewModel(WeatherRepository(FakeWeatherApi(0)))
 vm.onCitySelected("Samarkand")
 assertEquals(WeatherUiState.Success(Weather("Samarkand", 21, "Cloudy")), vm.uiState.value)
 vm.onCitySelected("Atlantis")
 assertEquals(WeatherUiState.Error("No forecast for Atlantis"), vm.uiState.value)
 }
}