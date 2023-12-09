package com.dicoding.calofruit.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.calofruit.retrofit.UserModel
import com.dicoding.calofruit.repository.UserRepository

class WelcomeViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}