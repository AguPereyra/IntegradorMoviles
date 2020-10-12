package com.iua.agustinpereyra.view.userviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.STATE_EMAIL
import com.iua.agustinpereyra.controller.STATE_USERNAME
import kotlinx.android.synthetic.main.user_account_view_fragment.view.*

class UserAccountViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_account_view_fragment, container, false)

        // Get listener
        val listener = activity as userAccountFragmentListener

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


    interface userAccountFragmentListener {
        fun onCancelUserAccountViewClick() : Unit
        fun onSaveUserAccountViewClick() : Unit
        fun navigateToChangePassword()
    }
}