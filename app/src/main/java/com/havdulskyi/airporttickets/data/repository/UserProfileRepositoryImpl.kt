package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.entity.UserProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserProfileRepositoryImpl(private val appDatabase: AppDatabase) : UserProfileRepository {
    override suspend fun insertUser(userProfileEntity: UserProfileEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .userProfileDao()
                .insert(Mapping.mapEntityToModel(userProfileEntity))
        }
    }

    override suspend fun insertUsers(userProfileEntities: List<UserProfileEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .userProfileDao()
                .insertAll(userProfileEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun updateUser(userProfileEntity: UserProfileEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .userProfileDao()
                .insert(Mapping.mapEntityToModel(userProfileEntity))
        }
    }

    override suspend fun updateUsers(userProfileEntities: List<UserProfileEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .userProfileDao()
                .updateAll(userProfileEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun deleteUser(userProfileEntity: UserProfileEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .userProfileDao()
                .delete(Mapping.mapEntityToModel(userProfileEntity))
        }
    }

    override suspend fun deleteUsers(userProfileEntities: List<UserProfileEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .userProfileDao()
                .deleteAll(userProfileEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun observeUsers(): Flow<List<UserProfileEntity>> {
        return appDatabase.userProfileDao().usersFlow().flatMapLatest { list ->
            flowOf(list
                .filterNotNull()
                .map { Mapping.mapModelToEntity(it) })
        }.flowOn(Dispatchers.IO)
    }
}