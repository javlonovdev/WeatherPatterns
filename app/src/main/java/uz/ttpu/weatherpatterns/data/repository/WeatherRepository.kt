package uz.ttpu.weatherpatterns.data.repository

import uz.ttpu.weatherpatterns.data.api.WeatherApi
import uz.ttpu.weatherpatterns.data.model.Weather
class WeatherRepository(private val api: WeatherApi) {
 suspend fun getWeather(city: String): Result<Weather> = try {
 Result.success(api.fetchWeather(city))
 } catch (e: IllegalArgumentException) {
 Result.failure(e)
 }
}
