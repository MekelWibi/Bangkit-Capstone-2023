package com.dicoding.calofruit.ui.maps

import androidx.lifecycle.ViewModel
import com.dicoding.calofruit.repository.UserRepository

class MapsViewModel (private val userRepository: UserRepository) : ViewModel() {
    fun getListStoryLocation() = userRepository.getStoryWithLocation()
}