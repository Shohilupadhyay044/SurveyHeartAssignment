package com.example.surveyheartassignment.data

import com.example.surveyheartassignment.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(".")
   suspend fun getUsers(@Query("results") result: Int): Response<UserResponse>

}