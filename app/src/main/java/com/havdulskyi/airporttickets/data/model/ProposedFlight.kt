package com.havdulskyi.airporttickets.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProposedFlight(
    @PrimaryKey
    val id: String,
    val fromAirportId: String,
    val toAirportId: String,
    val arriveTime: String,
    val ticketCost: Double
)
