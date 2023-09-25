package com.example.austinlieandro_navigationdanapi.data.response

import com.google.gson.annotations.SerializedName

data class Response(
	@field:SerializedName("Response")
	val response: List<ResponseItem>
)

data class ResponseItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

)
