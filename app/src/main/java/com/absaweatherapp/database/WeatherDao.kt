package com.absaweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert()
    suspend fun saveLocation(table : LocationTable)

    @Query("SELECT * FROM location")
    fun getLocations() : LiveData<List<LocationTable>>?
}