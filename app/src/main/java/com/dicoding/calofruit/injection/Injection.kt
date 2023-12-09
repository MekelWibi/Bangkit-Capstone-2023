package com.dicoding.calofruit.injection

import com.dicoding.calofruit.repository.UserRepository
import android.content.Context
import android.util.Log
import com.dicoding.calofruit.R
import com.dicoding.calofruit.database.StoryDatabase
import com.dicoding.calofruit.retrofit.ApiConfig
import com.dicoding.calofruit.utils.UserPreference
import com.dicoding.calofruit.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        Log.d(context.getString(R.string.token_disimpan), user.token)
        val apiService = ApiConfig.getApiService(user.token)
        val storyDatabase = StoryDatabase.getDatabase(context)
        return UserRepository(apiService, pref, storyDatabase)
    }

}