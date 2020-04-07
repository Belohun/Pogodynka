package com.example.pogodynka

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.iceandfireapi.data.network.WeatherApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.city_weather_info.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.math.roundToInt
import kotlin.time.days

class weatherInoFragment: Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.city_weather_info, container, false)
        val cityArg = weatherInoFragmentArgs.fromBundle(arguments!!)
        val city = cityArg.City
        val apiService = WeatherApiService()
        var day = LocalDate.now().dayOfWeek.name
       day = day.toLowerCase()
        day = day.capitalize()
        root.wi_day.text = day
        root.wi_city.text = city

        GlobalScope.launch(Dispatchers.Main) {
            val weatherResponse = apiService.getWeather(city).await()

            val url: String = "https://openweathermap.org/img/w/" + weatherResponse.weather[0].icon + ".png"
            Picasso.get()
                .load(url)
               .resize(200,200)
                .into(root.wi_image)
            root.wi_temp.text= ((weatherResponse.main.temp - 273.15).roundToInt()).toString() + "°C"
            root.wi_desc.text= weatherResponse.weather[0].description.capitalize()
            root.wi_wind.text = weatherResponse.wind.speed.toString() + " m/s"
            if(weatherResponse.rain==null){
                root.wi_preciption.text="0%"
            }else {
                root.wi_preciption.text = weatherResponse.rain.toString() + "%"
            }
            if (weatherResponse.timezone>=0) {
                root.wi_timezone.text ="+" + (weatherResponse.timezone / 3600).toString() + " UTC"
            }else {
                root.wi_timezone.text =(weatherResponse.timezone / 3600).toString() + " UTC"
            }
            var sunrise  = dateFormatter(weatherResponse.sys.sunrise.toLong())
            sunrise = sunrise.replace(sunrise[2].toString(),(sunrise[2].toString().toInt() + (weatherResponse.timezone/3600)).toString())
            root.wi_sunrise.text =sunrise
            var sunset = dateFormatter(weatherResponse.sys.sunset.toLong())
            sunset = sunset.replace(sunset[2].toString(),(sunset[2].toString().toInt() + (weatherResponse.timezone/3600)).toString())
            root.wi_sunset.text = sunset
            root.wi_pressure.text = weatherResponse.main.pressure.toString() + " hPa"
        }
        return root
    }
    fun dateFormatter(epoch: Long): String {

        val date = Date(epoch * 1000L)
        val sdf = SimpleDateFormat(" HH:mm")
        return sdf.format(date)
    }

    override fun onResume() {
        super.onResume()
        var btn = activity!!.findViewById<FloatingActionButton>(R.id.floating_btn_add)
        btn.hide()
    }

    override fun onStop() {
        super.onStop()
        var btn = activity!!.findViewById<FloatingActionButton>(R.id.floating_btn_add)
        btn.show()
    }


}