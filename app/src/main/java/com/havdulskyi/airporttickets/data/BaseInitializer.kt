package com.havdulskyi.airporttickets.data

import java.time.LocalDate

interface BaseInitializer {
    suspend fun initializeForDate(now : LocalDate)
}