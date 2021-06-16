package com.havdulskyi.airporttickets.data.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProfile(
    @PrimaryKey
    val id: String,
    val nickName: String,
    val id3rdParty: String
)
