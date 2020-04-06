package com.example.iceandfireapi.data.network


import com.example.pogodynka.Network.WeatherApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.http.GET
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.Query

/*http://api.openweathermap.org/data/2.5/weather?q=Tychy&appid=acfc2c97cea61aff8e50c22329b0dfe7*/
interface WeatherApiService {
    //@Headers("Accept: application/vnd.anapioficeandfire+json; version=1")
    @GET("weather")
    fun getWeather(@Query("q")miasto:String="Tychy",@Query("appid")appid:String="acfc2c97cea61aff8e50c22329b0dfe7"):Deferred<WeatherApi>
    companion object{
        operator fun invoke(/*connectivityInterceptor: ConnectivityInterceptor*/): WeatherApiService {

            /* if key needed*/
/*   val requestInterceptor = Interceptor{chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                   .addQueryParameter("appid","acfc2c97cea61aff8e50c22329b0dfe7")
                 .build()
             val request = chain.request()
                 .newBuilder()
                 .url(url)
                 .build()
             return@Interceptor chain.proceed(request)
         }*/
            val okHttpClient= OkHttpClient.Builder()
                   //.addInterceptor(requestInterceptor)
                /* .addInterceptor(connectivityInterceptor)*/
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
             /*   .baseUrl("https://api.openweathermap.org/data/2.5/weather/?q=Tychy&appid=acfc2c97cea61aff8e50c22329b0dfe7/")*/
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}