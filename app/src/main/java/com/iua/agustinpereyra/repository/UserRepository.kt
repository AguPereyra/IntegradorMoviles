package com.iua.agustinpereyra.repository

import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.UserDAO
import com.iua.agustinpereyra.repository.database.entities.User

class UserRepository(private val userDao : UserDAO) {

    suspend fun insert(user : User) {
        userDao.insert(user)
    }

    suspend fun update(user : User) {
        userDao.update(user)
    }

    suspend fun getUser(email : String): LiveData<User> {
        return userDao.getUser(email)
    }

    suspend fun updateUsername(username: String, email: String) {
        userDao.updateUsername(username, email)
    }
}