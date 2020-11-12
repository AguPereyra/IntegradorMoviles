package com.iua.agustinpereyra.view.welcomeviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.AccountManager
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.controller.STATE_PASSWORD
import com.iua.agustinpereyra.controller.STATE_USERNAME
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Get listener from parent activity
        val listener = activity as LoginFragmentListener

        // Set error if password is not valid
        view.login_button.setOnClickListener {
            // TODO: Shouldn't go elsewhere?
            val email = view?.login_username_input_edit_text?.text.toString()
            val passwd = view?.login_password_edit_text?.text.toString()
            if(AccountManager.isLoginValid(email, passwd, context)) {
                // Save to preferences
                val preferenceUtils = PreferenceUtils(context)
                preferenceUtils.saveLoggedUser(email, passwd)
                listener.navigateToMainPage()
            } else {
                // Show login error message
                login_password_input_container.error = getString(R.string.wrong_login)
            }
        }

        // Clear the error when the right amount of chars is set
        view.login_password_edit_text.doOnTextChanged { text, start, before, count ->
            login_password_input_container.error = null
        }


        // Set register button onclick
        view.login_register_button.setOnClickListener{
            listener.nagivateToRegisterPage()
        }

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            view.login_username_input_edit_text.setText(savedInstanceState.getString(STATE_USERNAME))
            view.login_password_edit_text.setText(savedInstanceState.getString(STATE_PASSWORD))
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_USERNAME, view?.login_username_input_edit_text?.text.toString())
            putString(STATE_PASSWORD, view?.login_password_edit_text?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    interface LoginFragmentListener {
        fun navigateToMainPage() : Unit
        fun nagivateToRegisterPage() : Unit
    }
}