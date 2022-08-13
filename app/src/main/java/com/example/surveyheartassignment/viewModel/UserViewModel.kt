package com.example.surveyheartassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surveyheartassignment.model.Results
import com.example.surveyheartassignment.model.UserResponse
import com.example.surveyheartassignment.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel (private val userRepository: UserRepository) : ViewModel(){

    fun getUserList(query: String?) {
        viewModelScope.launch{
           userRepository.getUser(50)
        }
    }

    val users : MutableLiveData<ArrayList<Results>>
    get() = userRepository.users
}