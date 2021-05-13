package com.absaweatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    @Expose
    private var _3h: Double?
) {

    fun get3h(): Double? {
        return _3h
    }

    fun set3h(_3h: Double?) {
        this._3h = _3h
    }
}