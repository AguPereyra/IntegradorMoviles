package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.UsersDAO
import com.iua.agustinpereyra.repository.database.entities.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(private val application: Application) {

    private val usersDao: UsersDAO

    init {
        val db = AppDatabase.getDatabase(application)
        usersDao = db.usersDao()
    }

    /**
     * getUserById gets the user asynchronously and returns observable data from DB as LiveData<Users>
     */
    suspend fun getUserById(userId: Int): LiveData<Users> = withContext(Dispatchers.IO) {
        val user = usersDao.getUserById(userId)
        user
    }

    /**
     * getUserById gets the user asynchronously and returns non observable data from DB
     */
    suspend fun getUserByIdNotObservable(userId: Int): Users = withContext(Dispatchers.IO) {
        val user = usersDao.getUserByIdNotObservable(userId)
        user
    }

    /**
     * getUser gets the user from database if password and username match
     * a registry from it. If no user is found, null is returned
     */
    suspend fun getUser(email: String, passwd: String): Users? = withContext(Dispatchers.IO) {
        usersDao.getUser(email, passwd)
    }

    /**
     * insertUsers inserts the asked user asynchronously
     */
    suspend fun insertUser(user: Users) = withContext(Dispatchers.IO) {
        usersDao.insertUser(user)
    }

    /**
     * deleteUser deletes the user asynchronously, and returns the number
     * of deleted rows
     */
    suspend fun deleteUser(user: Users): Int = withContext(Dispatchers.IO) {
        usersDao.deleteUser(user)
    }

    /**
     * updateUserPassword updates the user's password.
     * Returns true if one password was changed, false otherwise
     */
    suspend fun updateUserPassword(userId: Int, password: String): Boolean = withContext(Dispatchers.IO) {
        val rowsAffected = usersDao.updatePasswd(password, userId)
        // Check if only 1 row was affected
        rowsAffected == 1
    }

    /**
     * updateUserName updates the user's username.
     * Returns true if one username was changed, false otherwise
     */
    suspend fun updateUserName(userId: Int, username: String): Boolean = withContext(Dispatchers.IO) {
        val rowsAffected = usersDao.updateUsername(username, userId)
        // Check if only 1 row was affected
        rowsAffected == 1
    }
}