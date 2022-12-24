package com.carloszaragoza.weatherapp.feature_weather.presentation.screens.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloszaragoza.weatherapp.feature_weather.domain.model.favorites.Favorites
import com.carloszaragoza.weatherapp.feature_weather.presentation.screens.favorites.util.FavoritesEvents
import com.carloszaragoza.weatherapp.feature_weather.presentation.util.UiEvent
import com.carloszaragoza.weatherapp.feature_weather.domain.use_cases.FavoritesUseCases
import com.carloszaragoza.weatherapp.feature_weather.presentation.screens.favorites.util.FavoritesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCase: FavoritesUseCases,
    savedStatedHandle: SavedStateHandle,
):ViewModel() {

    val state by mutableStateOf(FavoritesState())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init{
        state.countryId = savedStatedHandle.get<String>("countryId")
        getFavorites()
    }

    private fun getFavorites()= viewModelScope.launch {
        useCase.getFavorites().distinctUntilChanged().collect{
            state.listOfFavorites.value = it
        }
    }

    fun onEvent(event: FavoritesEvents){
        when(event){
            is FavoritesEvents.OnNavigate ->{
                sendUiEvent(event = UiEvent.onNavigate(event.id))
            }
            is FavoritesEvents.OnPop ->{
                sendUiEvent(event = UiEvent.popBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}