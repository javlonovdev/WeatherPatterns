package uz.ttpu.weatherpatterns.mvi.view
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.ttpu.weatherpatterns.di.WeatherModule
import uz.ttpu.weatherpatterns.ui.WeatherScreen
import uz.ttpu.weatherpatterns.mvi.viewmodel.WeatherMviViewModel
import uz.ttpu.weatherpatterns.mvi.model.*
class MviActivity : ComponentActivity() {
 private val viewModel: WeatherMviViewModel by viewModels { WeatherModule.weatherMviViewModelFactory }
 override fun onCreate(savedInstanceState: Bundle?) {
 super.onCreate(savedInstanceState)
 setContent { MaterialTheme {
 val state by viewModel.state.collectAsStateWithLifecycle()
 WeatherScreen(state.toStatusText(), { viewModel.onIntent(WeatherIntent.SelectCity(it)) })
 } }
 }
}
internal fun WeatherState.toStatusText(): String = when {
 isLoading -> "Loading\u2026" // Loading takes priority over retained weather.
 error != null -> "Error: $error"
 weather != null -> "${weather.city}: ${weather.temperatureC}\u00B0C, ${weather.condition}"
 else -> "Pick a city"
}