package com.example.austinlieandro_navigationdanapi.data.retrofit

import com.example.austinlieandro_navigationdanapi.data.response.DetailResponse
import com.example.austinlieandro_navigationdanapi.data.response.ResponseItem
import com.example.austinlieandro_navigationdanapi.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    fun getAllUser():Call<List<ResponseItem>>

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ResponseItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ResponseItem>>
}