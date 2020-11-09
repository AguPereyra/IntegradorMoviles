package com.iua.agustinpereyra.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.iua.agustinpereyra.repository.database.entities.User

@Dao
interface UserDAO {
    @Insert
    fun insert(user : User)

    @Update
    fun update(user : User)

    @Query("UPDATE user SET username = :username WHERE email LIKE :email")
    fun updateUsername(username: String, email: String)

    @Query("SELECT * FROM user WHERE email LIKE :email")
    fun getUser(email : String) : LiveData<User>
}