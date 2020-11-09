package com.iua.agustinpereyra.view.welcomeviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        // Get listener from parent activity
        val listener = activity as RegisterFragmentListener

        // Set error if password is not valid
        view.register_button.setOnClickListener {
            if (!AccountManager.isPasswordValid(register_password_edit_text.text)) {
                register_password_input_container.error = getString(R.string.password_error)
            } else {
                // Clear the error
                register_password_input_container.error = null
                // Register
                val preferenceUtils = PreferenceUtils(context)
                val email = view?.register_email_edit_text?.text.toString()
                val username = view?.register_username_edit_text?.text.toString()
                val passwd = view?.register_password_edit_text?.text.toString()
                preferenceUtils.saveRegisteredUser(email, username, passwd)
                // Navigate
                listener.navigateToMainPage()
            }
        }

        // Clear the error when the right amount of chars is set
        view.register_password_edit_text.doOnTextChanged { text, start, before, count ->
            if (AccountManager.isPasswordValid(register_password_edit_text.text)) {
                // Clear the error message
                register_password_input_container.error = null
            }
            false
        }

        // Set on click function for Back button
        view.back_button.setOnClickListener{
            listener.nagivateToLoginPage()
        }

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            view.register_username_edit_text.setText(savedInstanceState.getString(STATE_USERNAME))
            view.register_password_edit_text.setText(savedInstanceState.getString(STATE_PASSWORD))
            view.register_email_edit_text.setText(savedInstanceState.getString(STATE_EMAIL))
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_USERNAME, view?.register_username_edit_text?.text.toString())
            putString(STATE_PASSWORD, view?.register_password_edit_text?.text.toString())
            putString(STATE_EMAIL, view?.register_email_edit_text?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    interface RegisterFragmentListener {
        fun navigateToMainPage() : Unit
        fun nagivateToLoginPage() : Unit
    }
}