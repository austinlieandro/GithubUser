package com.example.austinlieandro_navigationdanapi.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.austinlieandro_navigationdanapi.data.response.ItemsItem
import com.example.austinlieandro_navigationdanapi.data.response.ResponseItem
import com.example.austinlieandro_navigationdanapi.data.response.SearchResponse
import com.example.austinlieandro_navigationdanapi.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel(){

    private val _person = MutableLiveData<List<ResponseItem>>()
    val person: LiveData<List<ResponseItem>> = _person

    private val _search = MutableLiveData<List<ItemsItem>>()
    val search: LiveData<List<ItemsItem>> = _search

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        getUser()
        searchUser("")
    }

    fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllUser()
        client.enqueue(object : Callback<List<ResponseItem>> {
            override fun onResponse(
                call: Call<List<ResponseItem>>,
                response: Response<List<ResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _person.value = response.body()
                    Log.d(TAG, "Data: ${response.body()}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
//                    val allUsers = response.body() ?: emptyList()
//                    val searchUser = allUsers.filter { it.login.contains(query, ignoreCase = true) }
                    _search.value = response.body()?.items
                    Log.d(TAG, "Data: ${_search.value}")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}