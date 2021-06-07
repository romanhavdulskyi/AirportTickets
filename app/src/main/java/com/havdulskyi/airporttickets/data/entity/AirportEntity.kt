package com.havdulskyi.airporttickets.data.entity

import com.havdulskyi.airporttickets.data.model.City

data class AirportEntity(
    val id: String,
    val airportName: String,
    val airportCode: String,
    val city: City
)