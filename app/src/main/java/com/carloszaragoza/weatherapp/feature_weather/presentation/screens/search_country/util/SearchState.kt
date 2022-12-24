package com.carloszaragoza.weatherapp.feature_weather.presentation.screens.search_country.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.carloszaragoza.weatherapp.feature_weather.data.data_resource.DataOrException
import com.carloszaragoza.weatherapp.feature_weather.domain.model.country.Country
import com.carloszaragoza.weatherapp.feature_weather.domain.model.country.Data

data class SearchState(
    var countryData: MutableState<DataOrException<Country, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception(""))),
    var listFiltered:MutableState<List<Data>?> = mutableStateOf(emptyList())
)
