package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iua.agustinpereyra.controller.AccountManager
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val accountManager = AccountManager(application)
    // Variables used to check if user could be registered and can log in
    private val _isUserRegistered = MutableLiveData<Boolean>()
    val isUserRegistered: LiveData<Boolean> = _isUserRegistered

    fun registerUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            _isUserRegistered.value = accountManager.registerUser(email, username, password)
        }
    }
}