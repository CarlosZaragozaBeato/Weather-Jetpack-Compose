package com.carloszaragoza.weatherapp.feature_weather.domain.model.country

data class Country(
    var data: List<Data>,
    val error: Boolean,
    val msg: String
)