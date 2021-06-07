package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import com.havdulskyi.airporttickets.data.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun insertUser(userProfileEntity: UserProfileEntity)
    suspend fun insertUsers(userProfileEntities: List<UserProfileEntity>)

    suspend fun updateUser(userProfileEntity: UserProfileEntity)
    suspend fun updateUsers(userProfileEntities: List<UserProfileEntity>)

    suspend fun deleteUser(userProfileEntity: UserProfileEntity)
    suspend fun deleteUsers(userProfileEntities: List<UserProfileEntity>)

    suspend fun observeUsers() : Flow<List<UserProfileEntity>>
}