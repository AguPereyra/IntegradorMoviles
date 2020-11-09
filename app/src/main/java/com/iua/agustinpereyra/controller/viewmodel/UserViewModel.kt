package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.iua.agustinpereyra.repository.UserRepository
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    //TODO:Piegunta: Deberia hacer un repository por entidad?
    private val userRepository: UserRepository

    lateinit var currentUser: LiveData<User>

    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        userRepository = UserRepository(userDao)
    }

    /*
    * Launching database methods as corutines*/
    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.insert(user)
    }

    fun update(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.update(user)
    }

    fun updateUsername(username: String, email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.updateUsername(username, email)
    }

    fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.getUser(email)
    }

    fun setCurrentUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        currentUser = userRepository.getUser(email)
    }
}