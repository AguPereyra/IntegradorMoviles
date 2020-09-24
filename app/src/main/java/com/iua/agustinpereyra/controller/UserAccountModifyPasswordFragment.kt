package com.iua.agustinpereyra.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.utils.*
import kotlinx.android.synthetic.main.user_account_modify_password_fragment.view.*
import kotlinx.android.synthetic.main.user_account_view_fragment.view.*

class UserAccountModifyPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_account_modify_password_fragment, container, false)

        // Get listener
        val listener = activity as userAccountModifyPasswordFragmentListener

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

    interface userAccountModifyPasswordFragmentListener {
        fun onCancelModifyPasswordClick() : Unit
        fun onSaveModifyPasswordClick() : Unit
    }
}