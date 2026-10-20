package uz.ttpu.weatherpatterns

import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Test
import uz.ttpu.weatherpatterns.data.api.*
import uz.ttpu.weatherpatterns.data.model.Weather
import uz.ttpu.weatherpatterns.data.repository.WeatherRepository

import uz.ttpu.weatherpatterns.mvc.model.WeatherModel
import uz.ttpu.weatherpatterns.mvc.view.WeatherView
import uz.ttpu.weatherpatterns.mvc.controller.WeatherController
class WeatherControllerTest {
 @Test fun view_reads_loading_then_loaded_model() {
 val model = WeatherModel(WeatherRepository(FakeWeatherApi(0)))
 val events = mutableListOf<String>()
 val view = object : WeatherView {
 override fun render() { events += if (model.isLoading) "loading" else model.weather?.city ?: model.error.orEmpty() }
 }
 val controller = WeatherController(model, view, CoroutineScope(Dispatchers.Unconfined))
 controller.onCitySelected("Tashkent")
 assertEquals(listOf("loading", "Tashkent"), events)
 controller.destroy()
 }
}