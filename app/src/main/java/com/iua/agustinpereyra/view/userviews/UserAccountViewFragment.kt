package com.iua.agustinpereyra.view.userviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.controller.STATE_EMAIL
import com.iua.agustinpereyra.controller.STATE_USERNAME
import com.iua.agustinpereyra.controller.viewmodel.UserViewModel
import com.iua.agustinpereyra.repository.database.entities.User
import com.iua.agustinpereyra.view.base.ActionBarModifier
import kotlinx.android.synthetic.main.fragment_user_account.view.*
import java.lang.Exception

class UserAccountViewFragment : Fragment() {

    private lateinit var accountUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_account, container, false)

        // Get listener
        val listener = activity as UserAccountFragmentListener

        // Set toolbar title and back button
        listener.setActionBarTitle(getString(R.string.user_account_title))
        listener.setActionBarHomeButtonAsUp()

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            view.user_account_username_edit_text.setText(savedInstanceState.getString(STATE_USERNAME))
            view.user_account_email_edit_text.setText(savedInstanceState.getString(STATE_EMAIL))
        }

        // Initialize viewModel
        accountUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Set the current user based on preferences
        val preferenceUtils = PreferenceUtils(context)
        val prefCurrentUser = preferenceUtils.getLoggedUser()
            ?: throw Exception("There is no logged user registry while at user account view!")

        accountUserViewModel.setCurrentUser(prefCurrentUser.email)
        // Observe currentUser
        accountUserViewModel.currentUser.observe(viewLifecycleOwner, Observer { user ->
            // Update user data
            view?.user_account_email_edit_text?.setText(user.email)
            view?.user_account_username_edit_text?.setText(user.username)
        })

        // Set click listeners
        view.user_account_cancel_button.setOnClickListener{
            listener.onCancelUserAccountViewClick()
        }

        view.user_account_save_button.setOnClickListener{
            // Persist and then change screen

            val username = view?.user_account_username_edit_text?.text.toString()
            accountUserViewModel.updateUsername(username, prefCurrentUser.email)
            listener.onSaveUserAccountViewClick()
        }

        view.user_account_password_edit_text.setOnTouchListener { view, motionEvent ->
            // Navigate to the change password screen
            listener.navigateToChangePassword()
            view.performClick()
            true
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_USERNAME, view?.user_account_username_edit_text?.text.toString())
            putString(STATE_EMAIL, view?.user_account_email_edit_text?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    interface UserAccountFragmentListener : ActionBarModifier {
        fun onCancelUserAccountViewClick() : Unit
        fun onSaveUserAccountViewClick() : Unit
        fun navigateToChangePassword()
    }
}