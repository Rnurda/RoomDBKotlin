package com.example.roomdatabasekotlin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(chapter: UserModel)

    @Query("SELECT * FROM UserTable ORDER BY user_name ASC")
    fun getAllChapter(): List<UserModel>


    @Query("DELETE FROM UserTable")
    fun deletealltable()


    @Query("DELETE FROM UserTable WHERE user_name = :userid")
    fun deletetablebyid(userid: String?)


    @Query("UPDATE UserTable SET user_name=:userid WHERE user_name = :id")
    fun updateTable(userid: String?,id: String?)
}