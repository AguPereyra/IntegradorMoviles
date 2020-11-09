package com.iua.agustinpereyra.view.userviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.view.base.ActionBarModifier
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

        // Set toolbar title and back button
        // TODO: Ask if there is a better way to do this
        listener.setActionBarTitle(getString(R.string.user_account_passwd_title))
        listener.setActionBarHomeButtonAsUp()

        // Set click listener
        view.user_account_modify_passwd_cancel_button.setOnClickListener {
            listener.onCancelModifyPasswordClick()
        }

        view.user_account_modify_passwd_save_button.setOnClickListener {
            // Change passwd if possible
            val oldPasswd = view.user_account_modify_passwd_old_edit_text.text.toString()
            val newPasswd = view.user_account_modify_passwd_new_edit_text.text.toString()
            val confirmPasswd = view.user_account_modify_passwd_validate_edit_text.text.toString()
            if (AccountManager.changePasswd(oldPasswd, newPasswd, confirmPasswd, context)) {
                listener.onSaveModifyPasswordClick()
            } else {
                view.user_account_modify_passwd_validate_container.error = getString(R.string.error_change_passwd)
            }

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

    interface UserAccountModifyPasswordFragmentListener : ActionBarModifier {
        fun onCancelModifyPasswordClick() : Unit
        fun onSaveModifyPasswordClick() : Unit
    }
}