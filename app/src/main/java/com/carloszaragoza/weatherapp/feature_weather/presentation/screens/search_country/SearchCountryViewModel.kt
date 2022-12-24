package com.carloszaragoza.weatherapp.feature_weather.presentation.screens.search_country

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloszaragoza.weatherapp.feature_weather.presentation.navigation.Routes
import com.carloszaragoza.weatherapp.feature_weather.presentation.screens.search_country.util.SearchCountryEvents
import com.carloszaragoza.weatherapp.feature_weather.presentation.util.UiEvent
import com.carloszaragoza.weatherapp.feature_weather.data.data_resource.DataOrException
import com.carloszaragoza.weatherapp.feature_weather.data.repository.CountryRepository
import com.carloszaragoza.weatherapp.feature_weather.domain.model.country.Country
import com.carloszaragoza.weatherapp.feature_weather.domain.model.country.Data
import com.carloszaragoza.weatherapp.feature_weather.presentation.screens.search_country.util.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCountryViewModel @Inject constructor(
    private val repository: CountryRepository
):ViewModel() {


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _searchByTitle =  mutableStateOf("")
    val searchByTitle: State<String> = _searchByTitle

    val state =  MutableStateFlow(SearchState())


    init{
        searchCountry()
    }

    private fun searchCountry() = viewModelScope.launch {

            state.value.countryData.value.loading = true
            state.value.countryData.value = repository.getCountry()

            if (state.value.countryData.value.data.toString().isNotEmpty()) {
                state.value.countryData.value.loading = false
            }

    }


    fun onEvent(event: SearchCountryEvents){
        when(event){
            is SearchCountryEvents.onChangeCountryFilter -> {
                _searchByTitle.value = event.filter
                state.value.listFiltered.value = state.value.countryData.value.data?.data?.filter{ country ->
                    country.name.uppercase().contains(event.filter.uppercase())
                }!!
            }
            is SearchCountryEvents.onNavigate -> {
                sendUiEvent(event = UiEvent.onNavigate("${Routes.WEATHER.name}/${event.id}"))
            }
        }
    }

    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}