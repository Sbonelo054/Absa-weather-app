package com.absaweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.absaweatherapp.R
import com.absaweatherapp.adapter.LocationHistoryAdapter.LocationHistoryViewHolder
import com.absaweatherapp.database.LocationTable
import java.util.*

class LocationHistoryAdapter : RecyclerView.Adapter<LocationHistoryViewHolder>() {
    private var locations: List<LocationTable>? = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return LocationHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationHistoryViewHolder, position: Int) {
        val location = locations!![position]
        holder.location.text = location.location
    }

    fun setData(locations: List<LocationTable>?) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (locations != null) {
            locations!!.size
        } else 0
    }

    inner class LocationHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var location: TextView = itemView.findViewById(R.id.city_name)
    }
}