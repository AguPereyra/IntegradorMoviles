package com.iua.agustinpereyra.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.iua.agustinpereyra.repository.database.entities.Users

@Dao
interface UsersDAO {
    @Insert
    fun insertUser(user: Users)

    @Query("SELECT * FROM users WHERE id=:userId")
    fun getUser(userId: Int): LiveData<Users>

    @Query("UPDATE users SET username=:username WHERE id=:userId")
    fun updateUser(username: String, userId: Int)

    @Delete
    fun deleteUser(user: Users): Int
}