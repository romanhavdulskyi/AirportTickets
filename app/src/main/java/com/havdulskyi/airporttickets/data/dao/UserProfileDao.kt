package com.havdulskyi.airporttickets.data.dao

import androidx.room.*
import com.havdulskyi.airporttickets.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userProfile: UserProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userProfiles: List<UserProfile>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(userProfile: UserProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(userProfiles: List<UserProfile>)

    @Delete
    fun delete(userProfile: UserProfile)

    @Delete
    fun deleteAll(userProfiles: List<UserProfile>)

    @Query("SELECT * FROM UserProfile")
    fun usersFlow(): Flow<List<UserProfile?>>
}