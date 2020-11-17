package com.example.testtaskusers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testtaskusers.model.UserModel
import com.example.testtaskusers.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository = UserRepository(application.applicationContext)
    val users: LiveData<List<UserModel>> by lazy {
        loadUsers()
    }

    private fun loadUsers(): LiveData<List<UserModel>> {
        return repository.getUsers()
    }

    fun update() {
        viewModelScope.launch { repository.updateDatabase() }
    }
}