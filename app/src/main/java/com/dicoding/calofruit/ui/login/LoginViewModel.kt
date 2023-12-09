package com.dicoding.calofruit.ui.login

import com.dicoding.calofruit.repository.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.calofruit.retrofit.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun loginUser(email: String, password: String)=
        userRepository.login(email, password)

    fun saveSession(user: UserModel)=
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
}