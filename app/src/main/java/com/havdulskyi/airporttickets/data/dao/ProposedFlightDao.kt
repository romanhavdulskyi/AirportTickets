package com.havdulskyi.airporttickets.data.dao

import androidx.room.*
import com.havdulskyi.airporttickets.data.model.ProposedFlight
import kotlinx.coroutines.flow.Flow

@Dao
interface ProposedFlightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(proposedFlight: ProposedFlight)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(proposedFlights: List<ProposedFlight>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(proposedFlight: ProposedFlight)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(proposedFlights: List<ProposedFlight>)

    @Delete
    fun delete(proposedFlight: ProposedFlight)

    @Delete
    fun deleteAll(proposedFlights: List<ProposedFlight>)

    @Query("SELECT * FROM ProposedFlight")
    fun proposedFlightFlow(): Flow<List<ProposedFlight?>>

    @Query("SELECT * FROM ProposedFlight WHERE id=:flightId")
    fun fetchFlight(flightId: String): ProposedFlight?
}