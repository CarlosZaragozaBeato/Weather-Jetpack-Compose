package com.carloszaragoza.weatherapp.feature_weather.domain.model.week_time

data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)