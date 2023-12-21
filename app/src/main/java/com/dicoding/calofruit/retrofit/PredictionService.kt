package com.dicoding.calofruit.retrofit

import com.dicoding.calofruit.response.FruitResponse
import com.dicoding.calofruit.utils.PredictionResponse
import retrofit2.Call
import retrofit2.http.*
import okhttp3.MultipartBody

interface PredictionService {
    @Multipart
    @POST("prediction")
    fun uploadImage(@Part image: MultipartBody.Part): Call<PredictionResponse>

}
