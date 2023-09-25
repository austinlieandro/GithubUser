package com.example.austinlieandro_navigationdanapi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUsers::class], version = 1, exportSchema = false)
abstract class FavoriteRoomDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteRoomDatabase? = null
        fun getInstance(context: Context): FavoriteRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteRoomDatabase::class.java, "favorite_database2"
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
    }
}