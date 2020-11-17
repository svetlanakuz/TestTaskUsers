package com.example.testtaskusers.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.testtaskusers.`interface`.RetrofitServices
import com.example.testtaskusers.database.AppDatabase
import com.example.testtaskusers.model.Friend
import com.example.testtaskusers.model.UserModel
import com.example.testtaskusers.retrofit.RetrofitClient

class UserRepository(context: Context) {
    private val userService = RetrofitClient.getClient().create(RetrofitServices::class.java)
    private val userModelDao = AppDatabase.getInstance(context, this).userModelDao()

    fun getUsers(): LiveData<List<UserModel>> {
        return userModelDao.getAll()
    }

    fun getUserFriends(friends: List<Friend>): LiveData<List<UserModel>> {
        return userModelDao.getUserFriends(friends.map { it.id })
    }

    suspend fun updateDatabase() {
        userModelDao.synchronize(getUserList())
    }

    suspend fun getUserList(): List<UserModel> {
        return userService.getUserList("media", "e3672c23-b1a5-4ca7-bb77-b6580d75810c")
    }

}