package com.example.austinlieandro_navigationdanapi.di

import android.content.Context
import com.example.austinlieandro_navigationdanapi.database.FavoriteRoomDatabase
import com.example.austinlieandro_navigationdanapi.repository.FavoriteRepository
import com.example.austinlieandro_navigationdanapi.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val database = FavoriteRoomDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(dao, appExecutors)
    }
}