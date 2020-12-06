package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.iua.agustinpereyra.controller.AccountManager
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val accountManager = AccountManager(application)

    fun registerUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            accountManager.registerUser(email, username, password)
        }
    }
}