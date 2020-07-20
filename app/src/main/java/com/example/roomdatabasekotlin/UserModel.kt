package com.example.roomdatabasekotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class UserModel(
    @PrimaryKey
    @ColumnInfo(name = "user_name") val userName: String
)