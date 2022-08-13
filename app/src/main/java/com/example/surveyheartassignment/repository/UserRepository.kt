package com.example.surveyheartassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.surveyheartassignment.data.ApiService
import com.example.surveyheartassignment.model.Results
import com.example.surveyheartassignment.model.UserResponse

class UserRepository(private val apiService: ApiService) {

    private val userLiveData = MutableLiveData<ArrayList<Results>>()

    val users : MutableLiveData<ArrayList<Results>>
    get() = userLiveData

    suspend fun getUser(query : Int){
        val result = apiService.getUsers(query)
        if(result?.body() != null) {
          userLiveData.postValue(result.body()!!.userList!!)
        }
    }
}