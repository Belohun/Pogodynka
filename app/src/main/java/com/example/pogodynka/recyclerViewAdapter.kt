package com.example.pogodynka

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Layout
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.iceandfireapi.data.network.WeatherApiService
import com.example.pogodynka.Network.WeatherApi
import com.example.pogodynka.database.ApplicationRepository
import com.example.pogodynka.database.City
import com.example.pogodynka.database.CityDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.city_weather.view.cityTextView
import kotlinx.coroutines.*
import java.net.URL
import kotlin.math.roundToInt

class recyclerViewAdapter internal constructor(context:Context/*, var cities: LiveData<List<City>>*/) : RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>() {
   private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cities = emptyList<City>()
    var navController: NavController?=null




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cityTextView = itemView.findViewById<TextView>(R.id.cityTextView)
        var weatherImage:AppCompatImageView = itemView.findViewById(R.id.weatherImage)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.city_weather,parent,false)
        navController= Navigation.findNavController(parent)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val apiService = WeatherApiService()
        val current = cities[position]


       GlobalScope.launch(Dispatchers.Main) {
           val weatherResponse = apiService.getWeather(current.cityName).await()

           val url: String = "https://openweathermap.org/img/w/" + weatherResponse.weather[0].icon + ".png"
         //  val bm: Bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
           holder.cityTextView.text = weatherResponse.name + "  " + (weatherResponse.main.temp - 273.15).roundToInt() + "°C"
           Picasso.get()
               .load(url)
               .resize(150,150)
               .into(holder.weatherImage)

        //   holder.weatherImage.setImageBitmap(bm)


          // Glide.with(holder.weatherImage.context).load("http://openweathermap.org/img/w/" + weatherResponse.weather[0].icon + ".png").into(holder.weatherImage)
       }
           holder.itemView.setOnClickListener{
               val action = contentMainDirections.actionNavigationContentMainToWeatherInoFragment(cities[position].cityName)
               navController!!.navigate(action)

           }


    }
    internal fun setCities ( cities: List<City>){
        this.cities = cities
        notifyDataSetChanged()
    }
    fun getCityAt(position: Int): City{
        return cities[position]
    }

    override fun getItemCount(): Int {
        d("cities.size",cities.size.toString())
       return cities.size

    }

}








