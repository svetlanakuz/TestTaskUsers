package com.example.testtaskusers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.testtaskusers.model.UserModel

@Dao
interface UserModelDao {
    @Query("SELECT * FROM userModel")
    fun getAll(): LiveData<List<UserModel>>

    @Query("SELECT * FROM userModel WHERE id IN (:usersId)")
    fun getUserFriends(usersId: List<Int>): LiveData<List<UserModel>>

    @Insert
    suspend fun insertAll(users: List<UserModel>)

    @Query("DELETE FROM userModel")
    suspend fun deleteAll()

    @Transaction
    suspend fun synchronize(users: List<UserModel>) {
        deleteAll()
        insertAll(users)
    }
}