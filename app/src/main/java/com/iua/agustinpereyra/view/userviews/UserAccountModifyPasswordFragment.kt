package com.iua.agustinpereyra.view.userviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import kotlinx.android.synthetic.main.fragment_user_account_modify_password.view.*

class UserAccountModifyPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_account_modify_password, container, false)

        // Get listener
        val listener = activity as UserAccountModifyPasswordFragmentListener

        // Set click listener
        view.user_account_modify_passwd_cancel_button.setOnClickListener {
            listener.onCancelModifyPasswordClick()
        }

        view.user_account_modify_passwd_save_button.setOnClickListener {
            listener.onSaveModifyPasswordClick()
        }

        // Check if there is save state and replace
        view.user_account_modify_passwd_new_edit_text.setText(savedInstanceState?.getString(
            STATE_PASSWORD_NEW))
        view.user_account_modify_passwd_old_edit_text.setText(savedInstanceState?.getString(
            STATE_PASSWORD_OLD))
        view.user_account_modify_passwd_validate_edit_text.setText(savedInstanceState?.getString(
            STATE_PASSWORD_VALIDATE))
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_PASSWORD_NEW, view?.user_account_modify_passwd_new_edit_text?.text.toString())
            putString(STATE_PASSWORD_OLD, view?.user_account_modify_passwd_old_edit_text?.text.toString())
            putString(STATE_PASSWORD_VALIDATE, view?.user_account_modify_passwd_validate_edit_text?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    interface UserAccountModifyPasswordFragmentListener {
        fun onCancelModifyPasswordClick() : Unit
        fun onSaveModifyPasswordClick() : Unit
    }
}