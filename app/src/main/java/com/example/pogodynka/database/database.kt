package com.example.pogodynka.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "citys")
data class City(@PrimaryKey var cityName: String)

@Dao
interface CityDao{
    @Insert
    fun insert(city: City)
    @Update
    fun update(city: City)
    @Delete
    fun delete(city: City)
    @Query("SELECT * FROM CITYS")
    fun selectAllCitys(): LiveData<List<City>>
}

@Database(entities = arrayOf(City::class), version = 1)
abstract class CityDatabase: RoomDatabase(){
    abstract fun  cityDao(): CityDao
    companion object{
        @Volatile
        private var INSTANCE: CityDatabase? = null
        fun getDatabase(context: Context): CityDatabase{
            val tempInstance = INSTANCE
            if (tempInstance !=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,CityDatabase::class.java,"city_database").allowMainThreadQueries().build()
                INSTANCE= instance
                return instance
            }
        }
    }
}