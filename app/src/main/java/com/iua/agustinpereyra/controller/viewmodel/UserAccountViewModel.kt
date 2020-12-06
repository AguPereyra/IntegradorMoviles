package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.iua.agustinpereyra.controller.AccountManager
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.repository.UsersRepository
import com.iua.agustinpereyra.repository.database.entities.Users
import kotlinx.coroutines.launch
import java.lang.Error

class UserAccountViewModel(application: Application) : AndroidViewModel(application) {

    private val preferenceUtils = PreferenceUtils(application)
    private val usersRepository = UsersRepository(application)
    private val accountManager = AccountManager(application)
    private val _passwordUpdated: MutableLiveData<Boolean> = MutableLiveData()
    val passwordUpdated: LiveData<Boolean> = _passwordUpdated
    lateinit var currentUser: LiveData<Users>

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val userId = preferenceUtils.getCurrentUser()
            if (userId != null) {
                currentUser = usersRepository.getUserById(userId)
            } else {
                throw Error("Error: While trying to get current user, no current user ID found.")
            }
        }
    }

    fun changeUsername(username: String) {
        viewModelScope.launch {
            val userId = preferenceUtils.getCurrentUser()
            if (userId != null) {
                accountManager.changeUsername(username)
            } else {
                throw Error("Error: While trying to update username. No current user ID found.")
            }
        }
    }

    fun changePassword(oldPassword: String, newPasswd: String, confPasswd: String) {
        viewModelScope.launch {
            val userId = preferenceUtils.getCurrentUser()
            if (userId != null) {
                _passwordUpdated.value = accountManager.changePasswd(oldPassword, newPasswd, confPasswd)
            } else {
                throw Error("Error: While trying to update password. No current user ID found.")
            }
        }
    }
}