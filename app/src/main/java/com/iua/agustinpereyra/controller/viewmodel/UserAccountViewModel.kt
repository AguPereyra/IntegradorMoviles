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
    private val _currentUser = MutableLiveData<Users>()

    fun getCurrentUser(): LiveData<Users> {
        val userId = preferenceUtils.getCurrentUser()
        if (userId != null) {
            viewModelScope.launch {
                _currentUser.value = usersRepository.getUserByIdNotObservable(userId)
            }
        } else {
            throw Error("Error: While trying to get current user, no current user ID found.")
        }
        return _currentUser
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
}