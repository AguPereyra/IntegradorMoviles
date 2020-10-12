package com.iua.agustinpereyra.controller

import android.text.Editable

class PasswordManager {
    companion object {
        fun isPasswordValid(text: Editable?) : Boolean {
            // Check if password is bigger than 8
            return text != null && text.length >= 8
        }
    }
}