package com.absaweatherapp.model

data class Weather(
     var cod: String?,
     var message: Int?,
     var cnt: Int?,
     var list: List<WeatherList>?,
     var city: City?
)