package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.iua.agustinpereyra.repository.UserRepository
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    //TODO:Piegunta: Deberia hacer un repository por entidad?
    private val userRepository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
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

    fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.getUser(email)
    }

    //TODO:Continue adding the basic population and data connection to user layouts
    //https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
}