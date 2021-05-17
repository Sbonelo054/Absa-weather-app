package com.absaweatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.absaweatherapp.model.Weather
import com.absaweatherapp.model.WeatherResults
import com.absaweatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repo:WeatherRepository) : ViewModel() {

    fun getWeather(place:String, unit : String) : MutableLiveData<WeatherResults<Weather>> {
        val data: MutableLiveData<WeatherResults<Weather>> = MutableLiveData()
        viewModelScope.launch {
            val results = repo.getWeather(place, unit)
            data.value = results
        }
        return data
    }
}