package com.havdulskyi.airporttickets.data.dao

import androidx.room.*
import com.havdulskyi.airporttickets.data.model.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cities: List<City>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(cities: List<City>)

    @Delete
    fun delete(city: City)

    @Delete
    fun deleteAll(cities: List<City>)

    @Query("SELECT * FROM City")
    fun cityFlow(): Flow<List<City?>>

    @Query("SELECT * FROM City WHERE id=:cityId")
    fun fetchCity(cityId: String): City?
}