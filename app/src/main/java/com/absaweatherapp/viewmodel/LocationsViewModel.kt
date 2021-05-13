package com.absaweatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.absaweatherapp.database.LocationTable
import com.absaweatherapp.repository.LocationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationsViewModel(private val repo : LocationsRepository) : ViewModel() {

    fun saveLocation(locationTable: LocationTable){
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveLocation(locationTable)
        }
    }

    fun getLocations() : LiveData<List<LocationTable>>?{
        return repo.getLocations()
    }
}