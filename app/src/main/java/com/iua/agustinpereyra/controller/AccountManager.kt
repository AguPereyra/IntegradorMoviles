package com.iua.agustinpereyra.controller

import android.content.Context
import android.text.Editable
import com.iua.agustinpereyra.repository.database.AppDatabase

class AccountManager {
    companion object {

        fun isPasswordValid(text: Editable?) : Boolean {
            // Check if password is bigger than 8
            return text != null && text.length >= 8
        }

        fun isLoginValid(email: String, passwd: String, context: Context?) : Boolean {
            val preferenceUtils = PreferenceUtils(context)
            // Check if email and password are correct
            val existingUser = preferenceUtils.getRegisteredUser()
            if (existingUser != null){
                if (existingUser.email == email && existingUser.passwd == passwd) {
                    return true
                }
            }
            return false
        }

        /**
         * Returns true if password could be changed, false otherway
         */
        fun changePasswd(oldPasswd: String, newPasswd: String, confPasswd: String, context: Context?) : Boolean {
            val preferenceUtils = PreferenceUtils(context)
            val registeredUser = preferenceUtils.getRegisteredUser()
            if (registeredUser?.passwd == oldPasswd) {
                if (newPasswd == confPasswd) {
                    preferenceUtils.changeRegisteredPasswd(newPasswd)
                    return true
                }
            }
            return false
        }
    }
}