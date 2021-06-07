package com.havdulskyi.airporttickets.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Airport(
    @PrimaryKey
    val id: String,
    val airportName: String,
    val airportCode: String,
    val cityId: String
)