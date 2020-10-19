package com.iua.agustinpereyra.view.userviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.STATE_EMAIL
import com.iua.agustinpereyra.controller.STATE_USERNAME
import com.iua.agustinpereyra.view.ActionBarModifier
import kotlinx.android.synthetic.main.fragment_user_account.view.*

class UserAccountViewFragment : Fragment() {
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

        // Set click listeners
        view.user_account_cancel_button.setOnClickListener{
            listener.onCancelUserAccountViewClick()
        }

        view.user_account_save_button.setOnClickListener{
            listener.onSaveUserAccountViewClick()
        }

        view.user_account_password_edit_text.setOnTouchListener { view, motionEvent ->
            // Navigate to the change password screen
            listener.navigateToChangePassword()
            view.performClick()
            true
        }

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            view.user_account_username_edit_text.setText(savedInstanceState.getString(STATE_USERNAME))
            view.user_account_email_edit_text.setText(savedInstanceState.getString(STATE_EMAIL))
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