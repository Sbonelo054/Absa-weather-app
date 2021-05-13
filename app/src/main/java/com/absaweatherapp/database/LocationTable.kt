package com.absaweatherapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
class LocationTable(var location: String) {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}