package com.iua.agustinpereyra.view.userviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.databinding.FragmentUserAccountModifyPasswordBinding
import com.iua.agustinpereyra.view.base.ActionBarModifier

class UserAccountModifyPasswordFragment : Fragment() {

    private var fragmentBinding: FragmentUserAccountModifyPasswordBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentUserAccountModifyPasswordBinding.inflate(inflater, container, false)

        // Get listener
        val listener = activity as UserAccountModifyPasswordFragmentListener

        // Set toolbar title and back button
        // TODO: Ask if there is a better way to do this
        listener.setActionBarTitle(getString(R.string.user_account_passwd_title))
        listener.setActionBarHomeButtonAsUp()

        // Set click listener
        fragmentBinding?.userAccountModifyPasswdCancelButton?.setOnClickListener {
            listener.onCancelModifyPasswordClick()
        }

        fragmentBinding?.userAccountModifyPasswdSaveButton?.setOnClickListener {
            // Change passwd if possible
            val oldPasswd = fragmentBinding?.userAccountModifyPasswdOldEditText?.text.toString()
            val newPasswd = fragmentBinding?.userAccountModifyPasswdNewEditText?.text.toString()
            val confirmPasswd = fragmentBinding?.userAccountModifyPasswdValidateEditText?.text.toString()
            if (AccountManager.changePasswd(oldPasswd, newPasswd, confirmPasswd, context)) {
                listener.onSaveModifyPasswordClick()
            } else {
                fragmentBinding?.userAccountModifyPasswdValidateContainer?.error = getString(R.string.error_change_passwd)
            }

        }

        // Check if there is save state and replace
        fragmentBinding?.userAccountModifyPasswdNewEditText?.setText(savedInstanceState?.getString(
            STATE_PASSWORD_NEW))
        fragmentBinding?.userAccountModifyPasswdOldEditText?.setText(savedInstanceState?.getString(
            STATE_PASSWORD_OLD))
        fragmentBinding?.userAccountModifyPasswdValidateEditText?.setText(savedInstanceState?.getString(
            STATE_PASSWORD_VALIDATE))
        return fragmentBinding?.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_PASSWORD_NEW, fragmentBinding?.userAccountModifyPasswdNewEditText?.text.toString())
            putString(STATE_PASSWORD_OLD, fragmentBinding?.userAccountModifyPasswdOldEditText?.text.toString())
            putString(STATE_PASSWORD_VALIDATE, fragmentBinding?.userAccountModifyPasswdValidateEditText?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Cleaning up any references to the binding class
        fragmentBinding = null
    }

    interface UserAccountModifyPasswordFragmentListener : ActionBarModifier {
        fun onCancelModifyPasswordClick() : Unit
        fun onSaveModifyPasswordClick() : Unit
    }
}