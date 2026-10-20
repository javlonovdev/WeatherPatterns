package uz.ttpu.weatherpatterns.mvc.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import uz.ttpu.weatherpatterns.di.WeatherModule
import uz.ttpu.weatherpatterns.ui.WeatherScreen
import uz.ttpu.weatherpatterns.mvc.model.WeatherModel
import uz.ttpu.weatherpatterns.mvc.controller.WeatherController
class MvcActivity : ComponentActivity(), WeatherView {
 private val model = WeatherModel(WeatherModule.repository)
 private val controller = WeatherController(model, this)
 private var status by mutableStateOf("Pick a city")
 override fun onCreate(savedInstanceState: Bundle?) {
 super.onCreate(savedInstanceState)
 setContent { MaterialTheme { WeatherScreen(status, controller::onCitySelected) } }
 }
 // No parameters: the View reads the Model directly. Passing Weather here would
 // remove that direct read and move presentation data delivery toward MVP.
 override fun render() {
 status = when {
 model.isLoading -> "Loading\u2026"
 model.error != null -> "Error: ${model.error}"
 model.weather != null -> model.weather!!.let { "${it.city}: ${it.temperatureC}\u00B0C, ${it.condition}" }
 else -> "Pick a city"
 }
 }
 override fun onDestroy() { controller.destroy(); super.onDestroy() }
}
