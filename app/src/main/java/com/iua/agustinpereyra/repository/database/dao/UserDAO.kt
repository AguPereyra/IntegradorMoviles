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
    fun insert(user : User) : LiveData<User>

    @Update
    fun update(user : User) : LiveData<User>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUser(email : String) : LiveData<User>
}