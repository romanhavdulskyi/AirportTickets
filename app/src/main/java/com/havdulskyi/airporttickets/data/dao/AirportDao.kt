package com.havdulskyi.airporttickets.data.dao

import androidx.room.*
import com.havdulskyi.airporttickets.data.model.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(airport: Airport)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(airports: List<Airport>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(airport: Airport)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(airports: List<Airport>)

    @Delete
    fun delete(airport: Airport)

    @Delete
    fun deleteAll(airports: List<Airport>)

    @Query("SELECT * FROM Airport")
    fun airportFlow(): Flow<List<Airport?>>

    @Query("SELECT * FROM Airport WHERE id=:airportId")
    fun fetchAirport(airportId: String): Airport?
}