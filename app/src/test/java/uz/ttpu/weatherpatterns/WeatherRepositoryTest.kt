package uz.ttpu.weatherpatterns

import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Test
import uz.ttpu.weatherpatterns.data.api.*
import uz.ttpu.weatherpatterns.data.model.Weather
import uz.ttpu.weatherpatterns.data.repository.WeatherRepository

class WeatherRepositoryTest {
 private val repository = WeatherRepository(FakeWeatherApi(0))
 @Test fun known_city_returns_success() = runBlocking<Unit> {
 val result = repository.getWeather("Tashkent")
 assertTrue(result.isSuccess)
 assertEquals(24, result.getOrThrow().temperatureC)
 }
 @Test fun unknown_city_returns_failure() = runBlocking<Unit> {
 assertTrue(repository.getWeather("Atlantis").isFailure)
 }
 @Test fun cancellation_is_not_wrapped_as_failure() = runBlocking<Unit> {
 val repository = WeatherRepository(object : WeatherApi {
 override suspend fun fetchWeather(city: String): Weather { throw CancellationException("Cancelled") }
 })
 try { repository.getWeather("Tashkent"); fail("Cancellation must propagate") }
 catch (_: CancellationException) { }
 }
}