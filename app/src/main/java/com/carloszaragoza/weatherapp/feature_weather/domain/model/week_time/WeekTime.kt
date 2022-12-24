package com.carloszaragoza.weatherapp.feature_weather.domain.model.week_time



data class WeekTime(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)