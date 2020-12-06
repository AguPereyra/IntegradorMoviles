package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iua.agustinpereyra.controller.AccountManager
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val accountManager = AccountManager(application)
    private val _isUserLogged = MutableLiveData<Boolean>(false)
    val isUserLogged: LiveData<Boolean> = _isUserLogged

    /**
     * checkAndLog checks if the user data is valid, and if it is,
     * it logs the user in and puts the LiveData variable isUserLogged on true.
     * If it is not valid it puts it in false.
     */
    fun checkAndLog(email: String, passwd: String) {
        viewModelScope.launch {
            _isUserLogged.value = accountManager.checkAndLog(email, passwd)
        }
    }
}