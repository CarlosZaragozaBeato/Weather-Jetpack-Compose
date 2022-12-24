package com.carloszaragoza.weatherapp.feature_weather.presentation.screens.favorites.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.carloszaragoza.weatherapp.feature_weather.domain.model.favorites.Favorites

data class FavoritesState(
    var countryId:String? = null,
    val listOfFavorites:MutableState<List<Favorites>?> = mutableStateOf(emptyList())
)
