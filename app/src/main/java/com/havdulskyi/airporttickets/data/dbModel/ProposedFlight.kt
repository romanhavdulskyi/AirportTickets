package com.havdulskyi.airporttickets.data.dbModel

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Airport::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("fromAirportId"),
    onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Airport::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("toAirportId"),
        onDelete = ForeignKey.CASCADE)])
data class ProposedFlight(
    @PrimaryKey
    val id: String,
    val fromAirportId: String,
    val toAirportId: String,
    val departureTime: String,
    val arriveTime: String,
    val ticketCost: Double
)
