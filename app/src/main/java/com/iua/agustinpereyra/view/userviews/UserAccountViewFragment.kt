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
import com.iua.agustinpereyra.view.base.ActionBarModifier
import kotlinx.android.synthetic.main.fragment_user_account.view.*
import java.lang.Exception

class UserAccountViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_account, container, false)

        // Preference manager used below
        val preferenceUtils = PreferenceUtils(context)

        // Get listener
        val listener = activity as UserAccountFragmentListener

        // Set toolbar title and back button
        listener.setActionBarTitle(getString(R.string.user_account_title))
        listener.setActionBarHomeButtonAsUp()

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            view.user_account_username_edit_text.setText(savedInstanceState.getString(STATE_USERNAME))
            view.user_account_email_edit_text.setText(savedInstanceState.getString(STATE_EMAIL))
        } else {
            val currentUser = preferenceUtils.getLoggedUser()
            view.user_account_username_edit_text.setText(currentUser?.username)
            view.user_account_email_edit_text.setText(currentUser?.email)
        }

        // Set click listeners
        view.user_account_cancel_button.setOnClickListener{
            listener.onCancelUserAccountViewClick()
        }

        view.user_account_save_button.setOnClickListener{
            // Save new username, don't edit email
            val username = view?.user_account_username_edit_text?.text.toString()
            preferenceUtils.changeRegisteredUsername(username)
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