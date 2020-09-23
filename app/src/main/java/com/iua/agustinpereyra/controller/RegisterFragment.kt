package com.iua.agustinpereyra.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.utils.PasswordManager
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.android.synthetic.main.register_fragment.view.*

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_fragment, container, false)

        // Get listener from parent activity
        val listener = activity as registerFragmentListener

        // Set error if password is not between 8 and 16 chars
        view.register_button.setOnClickListener({
            if (!PasswordManager.isPasswordValid(register_password_edit_text.text)) {
                register_password_input_container.error = getString(R.string.password_error)
            } else {
                // Clear the error
                register_password_input_container.error = null

                // Navigate
                listener.navigateToMainPage()
            }
        })

        // Clear the error when the right amount of chars is set
        view.register_password_edit_text.setOnKeyListener({_, _, _ ->
            if (PasswordManager.isPasswordValid(register_password_edit_text.text)) {
                // Clear the error message
                register_password_input_container.error = null
            }
            false
        })

        // Set on click function for Back button
        view.back_button.setOnClickListener{
            listener.nagivateToLoginPage()
        }
        return view
    }

    interface registerFragmentListener {
        fun navigateToMainPage() : Unit
        fun nagivateToLoginPage() : Unit
    }
}