package uz.ttpu.weatherpatterns.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.ttpu.weatherpatterns.data.repository.WeatherRepository
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
 private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
 val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
 fun onCitySelected(city: String) {
 _uiState.value = WeatherUiState.Loading
 viewModelScope.launch {
 repository.getWeather(city).onSuccess { _uiState.value = WeatherUiState.Success(it) }
 .onFailure { _uiState.value = WeatherUiState.Error(it.message ?: "Unknown error") }
 }
 }
}
