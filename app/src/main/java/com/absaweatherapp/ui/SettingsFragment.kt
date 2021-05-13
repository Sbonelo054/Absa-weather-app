package com.absaweatherapp.ui

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.absaweatherapp.R
import com.absaweatherapp.adapter.LocationHistoryAdapter
import com.absaweatherapp.viewmodel.LocationsViewModel
import com.google.android.libraries.places.api.Places
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {
    private val viewModel by viewModel<LocationsViewModel>()
    private var adapter : LocationHistoryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Places.isInitialized()) {
            Places.initialize(this.requireContext(), "AIzaSyAzw4VUVV3j8wqGBtwqXqBhfVNgwf-oKkM")
        }
        Places.createClient(this.requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    private fun saveData(unit : String, measurement : String){
        val sharedPreferences : SharedPreferences? = context?.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(TEMP_UNIT, unit)
        editor?.putString(MEASUREMENT_UNIT, measurement)
        editor?.apply()
    }

    private fun loadData() {
        val sharedPreferences : SharedPreferences? = context?.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        unit_name.text = sharedPreferences?.getString(TEMP_UNIT, "Celsius")
        measurement_unit.text = sharedPreferences?.getString(MEASUREMENT_UNIT,"metric")
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        unit_name.setOnClickListener {subView->
            val pm = PopupMenu(requireContext(), subView)
            pm.menuInflater.inflate(R.menu.temperature_menu, pm.menu)
            pm.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.celsius_menu -> {
                        saveData("Celsius", "metric")
                        loadData()
                        true
                    }
                    R.id.fahrenheits_menu -> {
                        saveData("Fahrenheit","imperial")
                        loadData()
                        true
                    }
                    else -> false
                }
            }
            pm.show()
        }

        viewModel.getLocations()?.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                adapter = LocationHistoryAdapter()
                recycler_view.setHasFixedSize(true)
                recycler_view.adapter = adapter
                adapter?.setData(it)
                val linearLayoutManager = LinearLayoutManager(requireActivity())
                recycler_view.layoutManager = linearLayoutManager

                selected_city.text = it[it.size - 1].location
            }
        }
    }

    companion object{
        const val SHARED_PREFS = "sharedPrefs"
        const val TEMP_UNIT = "temperature"
        const val MEASUREMENT_UNIT = "measurement"
    }
}