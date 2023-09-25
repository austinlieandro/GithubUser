package com.example.austinlieandro_navigationdanapi.repository

import androidx.lifecycle.LiveData
import com.example.austinlieandro_navigationdanapi.database.FavoriteDao
import com.example.austinlieandro_navigationdanapi.database.FavoriteUsers
import com.example.austinlieandro_navigationdanapi.utils.AppExecutors

class FavoriteRepository private constructor(
    private var favoriteDao:FavoriteDao,
    private val appExecutors: AppExecutors
){
    fun getFavoriteUser(): LiveData<List<FavoriteUsers>> {
        return favoriteDao.getAllFavoriteUser()
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUsers> {
        return favoriteDao.getFavoriteUserByUsername(username)
    }

    fun insertFavorite(favoriteUsers: FavoriteUsers) {
        appExecutors.diskIO.execute {
            favoriteDao.insert(favoriteUsers)
        }
    }

    fun deleteFavorite(favoriteUsers: FavoriteUsers) {
        appExecutors.diskIO.execute {
            favoriteDao.delete(favoriteUsers)
        }
    }

    companion object{
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(favoriteDao, appExecutors)
            }.also { instance = it }
    }
}