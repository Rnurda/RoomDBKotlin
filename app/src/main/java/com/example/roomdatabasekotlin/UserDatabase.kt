package com.example.roomdatabasekotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(UserModel::class), version = 1)

abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        UserDatabase::class.java, "user_database.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}