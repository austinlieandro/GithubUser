package com.example.austinlieandro_navigationdanapi.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.austinlieandro_navigationdanapi.database.FavoriteUsers
import com.example.austinlieandro_navigationdanapi.repository.FavoriteRepository

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {
    private val _favoriteUsers = MutableLiveData<List<FavoriteUsers>>()
    val favoriteUsers: LiveData<List<FavoriteUsers>> = _favoriteUsers

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFavoriteUsers() {
        _isLoading.postValue(true)
        favoriteRepository.getFavoriteUser().observeForever { favoriteUsersList ->
            _favoriteUsers.postValue(favoriteUsersList)
            _isLoading.postValue(false)
            Log.d("FavoriteViewModel", "Favorite Users: $favoriteUsersList")
        }
    }

    fun checkIsFavorite(username: String) {
        favoriteRepository.getFavoriteUserByUsername(username).observeForever { favoriteUser ->
            val isFavorite = favoriteUser != null
            _isFavorite.postValue(isFavorite)
        }
    }

    fun addFavoriteUser(favoriteUsers: FavoriteUsers) {
        favoriteRepository.insertFavorite(favoriteUsers)
    }

    fun removeFavoriteUser(favoriteUsers: FavoriteUsers) {
        favoriteRepository.deleteFavorite(favoriteUsers)
    }
}