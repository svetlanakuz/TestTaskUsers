package com.example.testtaskusers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testtaskusers.model.Friend
import com.example.testtaskusers.model.UserModel
import com.example.testtaskusers.repository.UserRepository
import kotlinx.coroutines.launch


class FriendViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var users: LiveData<List<UserModel>>
    private val repository: UserRepository = UserRepository(application.applicationContext)

    fun getUsers(friends: List<Friend>): LiveData<List<UserModel>> {
        viewModelScope.launch { users = repository.getUserFriends(friends) }
        return users
    }
}



