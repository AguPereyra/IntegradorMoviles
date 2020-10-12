package com.iua.agustinpereyra.view.welcomeviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.PasswordManager
import com.iua.agustinpereyra.controller.STATE_PASSWORD
import com.iua.agustinpereyra.controller.STATE_USERNAME
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        // Get listener from parent activity
        val listener = activity as loginFragmentListener

        // Set error if password is not between 8 and 16 chars
        view.login_button.setOnClickListener({
            if (!PasswordManager.isPasswordValid(password_edit_text.text)) {
                password_input_container.error = getString(R.string.password_error)
            } else {
                // Clear the error
                password_input_container.error = null

                // Navigate
                listener.navigateToMainPage()
            }
        })

        // Clear the error when the right amount of chars is set
        view.password_edit_text.setOnKeyListener({_, _, _ ->
            if (PasswordManager.isPasswordValid(password_edit_text.text)) {
                // Clear the error message
                password_input_container.error = null
            }
            false
        })

        // Set register button onclick
        view.login_register_button.setOnClickListener{
            listener.nagivateToRegisterPage()
        }

        // Check if data was stored and rewrite in that case
        view.username_input_edit_text.setText(savedInstanceState?.getString(STATE_USERNAME))
        view.password_edit_text.setText(savedInstanceState?.getString(STATE_PASSWORD))

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_USERNAME, view?.username_input_edit_text?.text.toString())
            putString(STATE_PASSWORD, view?.password_edit_text?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    interface loginFragmentListener {
        fun navigateToMainPage() : Unit
        fun nagivateToRegisterPage() : Unit
    }
}