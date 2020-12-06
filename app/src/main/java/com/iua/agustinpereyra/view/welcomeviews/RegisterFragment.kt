package com.iua.agustinpereyra.view.welcomeviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.controller.viewmodel.RegisterViewModel
import com.iua.agustinpereyra.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var fragmentBinding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentRegisterBinding.inflate(inflater, container, false)

        // Get listener from parent activity
        val listener = activity as RegisterFragmentListener

        // Get ViewModel
        val registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        // Set error if password is not valid
        fragmentBinding?.registerButton?.setOnClickListener {
            if (!AccountManager.isPasswordValid(fragmentBinding?.registerPasswordEditText?.text)) {
                fragmentBinding?.registerEmailInputContainer?.error = getString(R.string.password_error)
            } else {
                // Clear the error
                fragmentBinding?.registerPasswordInputContainer?.error = null
                // Register
                val preferenceUtils = PreferenceUtils(context)
                val email = fragmentBinding?.registerEmailEditText?.text.toString()
                val username = fragmentBinding?.registerUsernameEditText?.text.toString()
                val passwd = fragmentBinding?.registerPasswordEditText?.text.toString()

                // Register user
                registerViewModel.registerUser(email, username, passwd)

                // Navigate
                listener.navigateToMainPage()
            }
        }

        // Clear the error when the right amount of chars is set
        fragmentBinding?.registerPasswordEditText?.doOnTextChanged { text, start, before, count ->
            if (AccountManager.isPasswordValid(fragmentBinding?.registerPasswordEditText?.text)) {
                // Clear the error message
                fragmentBinding?.registerPasswordInputContainer?.error = null
            }
            false
        }

        // Set on click function for Back button
        fragmentBinding?.backButton?.setOnClickListener{
            listener.nagivateToLoginPage()
        }

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            fragmentBinding?.registerUsernameEditText?.setText(savedInstanceState.getString(STATE_USERNAME))
            fragmentBinding?.registerPasswordEditText?.setText(savedInstanceState.getString(STATE_PASSWORD))
            fragmentBinding?.registerEmailEditText?.setText(savedInstanceState.getString(STATE_EMAIL))
        }

        return fragmentBinding?.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_USERNAME, fragmentBinding?.registerUsernameEditText?.text.toString())
            putString(STATE_PASSWORD, fragmentBinding?.registerPasswordEditText?.text.toString())
            putString(STATE_EMAIL, fragmentBinding?.registerEmailEditText?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cleaning up any references to the binding class
        fragmentBinding = null
    }

    interface RegisterFragmentListener {
        fun navigateToMainPage() : Unit
        fun nagivateToLoginPage() : Unit
    }
}