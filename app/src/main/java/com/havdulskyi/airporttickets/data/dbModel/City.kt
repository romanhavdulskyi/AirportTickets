package com.havdulskyi.airporttickets.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class City(
    @PrimaryKey
    val id: String,
    val city: String,
    val country: String
)
