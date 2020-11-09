package com.iua.agustinpereyra.controller

import android.text.Editable
import com.iua.agustinpereyra.repository.database.AppDatabase

class LoginManager {
    companion object {

        fun isPasswordValid(text: Editable?) : Boolean {
            // Check if password is bigger than 8
            return text != null && text.length >= 8
        }

        fun isLoginValid(email: String, passwd: String) : Boolean {
            //TODO:Implement filtering logic
            return true
        }
    }
}