package com.dicoding.calofruit.utils

import com.dicoding.calofruit.repository.UserRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.calofruit.injection.Injection
import com.dicoding.calofruit.ui.welcome.WelcomeViewModel
import com.dicoding.calofruit.ui.login.LoginViewModel
import com.dicoding.calofruit.ui.main.MainViewModel
import com.dicoding.calofruit.ui.register.RegisterViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository) as T
        }
        else if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userRepository) as T
        } else if(modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(userRepository) as T
        } else if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }
        throw IllegalArgumentException("No ModelClass: " + modelClass.name)
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(Injection.provideRepository(context))
        }
    }
}