package com.havdulskyi.airporttickets.view.initial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.havdulskyi.airporttickets.common.Event
import com.havdulskyi.airporttickets.data.entity.UserProfileEntity
import com.havdulskyi.airporttickets.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class InitialViewModel(private val userProfileRepository: UserProfileRepository) : ViewModel() {

    val navigateToMainActivity = MutableLiveData<Event<Boolean>>()
    val initialViewVisible = MutableLiveData<Boolean>(false)

    fun loginWithFirebase(firebaseUser: FirebaseUser) {
        viewModelScope.launch {
            userProfileRepository.insertUser(UserProfileEntity(
                id = UUID.randomUUID().toString(),
                nickName = firebaseUser.displayName ?: "User",
                id3rdParty = firebaseUser.uid
            ))
        }
    }

    fun loginAsGuest() {
        viewModelScope.launch {
            userProfileRepository.insertUser(UserProfileEntity(
                id = UUID.randomUUID().toString(),
                nickName = "User",
                id3rdParty = ""
            ))
        }
    }

    init {
        viewModelScope.launch {
            userProfileRepository
                .observeUsers()
                .collect { users ->
                    initialViewVisible.postValue(users.isEmpty())
                    if (users.isNotEmpty())
                        navigateToMainActivity.postValue(Event(true))
                }
        }
    }
}