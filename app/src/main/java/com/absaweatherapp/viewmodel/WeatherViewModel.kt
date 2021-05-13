package com.absaweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.absaweatherapp.model.Weather
import com.absaweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(val repo:WeatherRepository) : ViewModel() {

    fun getWeather(place:String, metric : String) : MutableLiveData<Weather> {
        val data: MutableLiveData<Weather> = MutableLiveData()
        viewModelScope.launch {
            val results = repo.getWeather(place, metric)
            data.value = results
        }
        return data
    }
}