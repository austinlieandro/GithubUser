package com.example.austinlieandro_navigationdanapi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteUsers(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    var username: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)