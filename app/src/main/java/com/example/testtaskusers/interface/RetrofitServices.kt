package com.example.testtaskusers.`interface`

import com.example.testtaskusers.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("/v0/b/candidates--questionnaire.appspot.com/o/users.json")
    suspend fun getUserList(
        @Query("alt") alt: String,
        @Query("token") token: String
    ): List<UserModel>
}