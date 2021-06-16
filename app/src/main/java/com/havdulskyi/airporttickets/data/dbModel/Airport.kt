package com.havdulskyi.airporttickets.data.dbModel

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = City::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("cityId"),
    onDelete = ForeignKey.CASCADE)])
data class Airport(
    @PrimaryKey
    val id: String,
    val airportName: String,
    val airportCode: String,
    val cityId: String
)