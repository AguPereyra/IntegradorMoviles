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

    // Preferred method as is faster
    @Query("SELECT * FROM users WHERE id=:userId")
    fun getUserById(userId: Int): LiveData<Users>

    // Variant for when LiveData is not needed
    @Query("SELECT * FROM users WHERE id=:userId")
    fun getUserByIdNotObservable(userId: Int): Users

    // Used to get user when id is not available, used to check login
    @Query("SELECT * FROM users WHERE email=:email AND passwd=:passwd")
    fun getUser(email: String, passwd: String): Users


    @Query("UPDATE users SET username=:username WHERE id=:userId")
    fun updateUsername(username: String, userId: Int)

    @Query("UPDATE users SET passwd=:passwd WHERE id=:userId")
    fun updatePasswd(passwd: String, userId: Int)

    @Delete
    fun deleteUser(user: Users): Int
}