package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iua.agustinpereyra.controller.AccountManager
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.repository.UsersRepository
import com.iua.agustinpereyra.repository.database.entities.Users
import kotlinx.coroutines.launch
import java.lang.Error

class UserAccountModifyPasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceUtils = PreferenceUtils(application)
    private val usersRepository = UsersRepository(application)
    private val accountManager = AccountManager(application)
    private val _isPasswordUpdated: MutableLiveData<Boolean> = MutableLiveData()
    val isPasswordUpdated: LiveData<Boolean> = _isPasswordUpdated

    private lateinit var currentUser: Users

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val userId = preferenceUtils.getCurrentUser()
            if (userId != null) {
                currentUser = usersRepository.getUserByIdNotObservable(userId)
            } else {
                throw Error("Error: While trying to get current user, no current user ID found.")
            }
        }
    }

    fun changePassword(oldPassword: String, newPasswd: String, confPasswd: String) {
        viewModelScope.launch {
            val userId = preferenceUtils.getCurrentUser()
            if (userId != null) {
                _isPasswordUpdated.value = accountManager.changePasswd(oldPassword, newPasswd, confPasswd)
            } else {
                throw Error("Error: While trying to update password. No current user ID found.")
            }
        }
    }

}