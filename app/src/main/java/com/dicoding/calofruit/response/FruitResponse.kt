package com.dicoding.calofruit.response

import com.google.gson.annotations.SerializedName

data class FruitResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: Status? = null
)

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("predicted_calorie")
	val predictedCalorie: Any? = null,

	@field:SerializedName("predicted_class")
	val predictedClass: String? = null
)
