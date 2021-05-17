package com.absaweatherapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.absaweatherapp.R
import com.absaweatherapp.database.LocationTable
import com.absaweatherapp.model.WeatherResults
import com.absaweatherapp.ui.SettingsFragment.Companion.MEASUREMENT_UNIT
import com.absaweatherapp.ui.SettingsFragment.Companion.SHARED_PREFS
import com.absaweatherapp.utils.Constants
import com.absaweatherapp.viewmodel.LocationsViewModel
import com.absaweatherapp.viewmodel.WeatherViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel by viewModel<WeatherViewModel>()
    private val locationsViewModel by viewModel<LocationsViewModel>()
    private var alert : Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Places.isInitialized()) {
            Places.initialize(this.requireContext(), Constants.PLACES_ID)
        }
        Places.createClient(this.requireContext())
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_more -> {
              findNavController().navigate(R.id.home_to_settings)
                true
            }

            R.id.menu_city -> {
                onSearch()
                true
            }
            else -> false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentDate = "Now"
        current_date.text = currentDate
        getHistory()
    }

    private fun getWeatherResults(location : String, unit : String) {
        viewModel.getWeather(location, unit).observe(viewLifecycleOwner, { response ->

            val error = response as? WeatherResults.Error
            if (error != null) {
                connectionError(error.error)
            }

            val success = (response as? WeatherResults.SuccessResults)?.data
            success?.let {
                Picasso.get()
                    .load(Constants.IMAGE_URL + it.list?.get(0)?.weather?.get(0)?.icon + "@2x.png")
                    .into(main_icon)
                val mainTemp = "${it.list?.get(0)?.main?.temp_max?.toInt().toString()} °"
                main_temp.text = mainTemp
                city.text = it.city?.name
                main_description.text = it.list?.get(0)?.weather?.get(0)?.description
                val cloudsData = "${it.list?.get(0)?.clouds?.all.toString()} %"
                clouds.text = cloudsData
                val windData = "${it.list?.get(0)?.wind?.deg.toString()} °"
                wind.text = windData
                val humidityData = "${it.list?.get(0)?.main?.humidity.toString()} %"
                humidity.text = humidityData
                val pressureData = "${it.list?.get(0)?.main?.pressure.toString()} hPa"
                pressure.text = pressureData
                val visibilityData = "${it.list?.get(0)?.visibility.toString()} ft"
                visibility.text = visibilityData
                val seaLevelData =  "${it.list?.get(0)?.main?.grnd_level.toString()} hPa"
                sea_level.text = seaLevelData

                Picasso.get()
                    .load(Constants.IMAGE_URL + it.list?.get(1)?.weather?.get(0)?.icon + "@2x.png")
                    .into(first_icon)
                Picasso.get()
                    .load(Constants.IMAGE_URL + it.list?.get(2)?.weather?.get(0)?.icon + "@2x.png")
                    .into(second_icon)
                Picasso.get()
                    .load(Constants.IMAGE_URL + it.list?.get(3)?.weather?.get(0)?.icon + "@2x.png")
                    .into(third_icon)
                Picasso.get()
                    .load(Constants.IMAGE_URL + it.list?.get(4)?.weather?.get(0)?.icon + "@2x.png")
                    .into(fourth_icon)

                first_date.text = it.list?.get(1)?.dt_txt?.substring(10, 16)
                second_date.text = it.list?.get(2)?.dt_txt?.substring(10, 16)
                third_date.text = it.list?.get(3)?.dt_txt?.substring(10, 16)
                fourth_date.text = it.list?.get(4)?.dt_txt?.substring(10, 16)

                val firstTempData = "${it.list?.get(1)?.main?.temp_max?.toInt().toString()} °"
                val secondTempData = "${it.list?.get(2)?.main?.temp_max?.toInt().toString()} °"
                val thirdTempData = "${it.list?.get(3)?.main?.temp_max?.toInt().toString()} °"
                val fourthTempData = "${it.list?.get(4)?.main?.temp_max?.toInt().toString()} °"

                first_temp.text = firstTempData
                second_temp.text = secondTempData
                third_temp.text = thirdTempData
                fourth_temp.text = fourthTempData
            }
        })
    }

    private fun connectionError(throwable: Throwable) {
        val showing = alert?.isShowing ?: false
        if(showing)
            return
        val message= throwable.toString()

        val title: String
        val content: String
        when {
            message.contains("java.net.UnknownHostException",true) -> {
                title =  "Internet Not Available"
                content = "Could not connect to the Internet. Please verify that you are connected and try again"
            }
            message.contains("java.net.SocketTimeoutException",true) -> {
                title =  "Connection Timeout"
                content = "Server took too long to respond. This may be caused by a bad network connection"
            }
            message.contains("javax.net.ssl.SSLPeerUnverifiedException", true) -> {
                title = "SSL Cert. Unverified"
                content = "Hostname not verified"
            }
            else -> {
                title = "Unknown Error"
                content = "An Unknown error has occurred. Please try again later"
            }
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(content)
            .setCancelable(true)
            .setPositiveButton("Retry") { dialog: DialogInterface?, _: Int ->
                getHistory()
                dialog?.dismiss()
            }

        alert = builder.create()
        alert?.show()
    }

    private fun getHistory() {
        locationsViewModel.getHistory()?.observe(viewLifecycleOwner, { data->
            if (data.isNotEmpty()) {
                val location = data[data.size - 1].location
                val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val unit = sharedPreferences?.getString(MEASUREMENT_UNIT, "metric") ?: ""
                getWeatherResults(location, unit)
            }else{
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Please add city")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { _: DialogInterface?, _: Int ->
                        onSearch()
                    }
                val alert = builder.create()
                alert.show()
            }
        })
    }

    private fun onSearch(){
        val fields: List<Place.Field> = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this.requireContext())
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null) {
            if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data)
                if(place.name != null){
                    val locations = LocationTable(place.name!!)
                    locationsViewModel.saveLocation(locations)
                    findNavController().navigateUp()
                }
            }else if(resultCode == AutocompleteActivity.RESULT_ERROR){
                val status= Autocomplete.getStatusFromIntent(data)
                Toast.makeText(context,status.statusMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}