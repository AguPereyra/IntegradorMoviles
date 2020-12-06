package com.iua.agustinpereyra.view.welcomeviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.STATE_PASSWORD
import com.iua.agustinpereyra.controller.STATE_USERNAME
import com.iua.agustinpereyra.controller.viewmodel.LoginViewModel
import com.iua.agustinpereyra.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var fragmentBinding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)

        // Get listener from parent activity
        val listener = activity as LoginFragmentListener

        // Get viewmodel
        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Observe isUserLogged variable
        loginViewModel.isUserLogged.observe(viewLifecycleOwner, { isLogged ->
            if (isLogged) {
                listener.navigateToMainPage()
            } else {
                // Show login error message
                fragmentBinding?.loginPasswordInputContainer?.error = getString(R.string.wrong_login)
            }
        })

        // Set error if password is not valid
        fragmentBinding?.loginButton?.setOnClickListener {
            val email = fragmentBinding?.loginUsernameInputEditText?.text.toString()
            val passwd = fragmentBinding?.loginPasswordEditText?.text.toString()
            // Asynchronously check if is valid
            // TODO: Is it okay this way?
            loginViewModel.checkAndLog(email, passwd)
        }

        // Clear the error when the right amount of chars is set
        fragmentBinding?.loginPasswordEditText?.doOnTextChanged { text, start, before, count ->
            fragmentBinding?.loginPasswordInputContainer?.error = null
        }


        // Set register button onclick
        fragmentBinding?.loginRegisterButton?.setOnClickListener{
            listener.nagivateToRegisterPage()
        }

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            fragmentBinding?.loginUsernameInputEditText?.setText(savedInstanceState.getString(STATE_USERNAME))
            fragmentBinding?.loginPasswordEditText?.setText(savedInstanceState.getString(STATE_PASSWORD))
        }

        return fragmentBinding?.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_USERNAME, fragmentBinding?.loginUsernameInputEditText?.text.toString())
            putString(STATE_PASSWORD, fragmentBinding?.loginUsernameInputEditText?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Cleaning up any references to the binding class
        fragmentBinding = null
    }

    interface LoginFragmentListener {
        fun navigateToMainPage() : Unit
        fun nagivateToRegisterPage() : Unit
    }
}