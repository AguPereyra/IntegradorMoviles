package com.iua.agustinpereyra.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
import kotlinx.android.synthetic.main.user_account_modify_password_fragment.view.*

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

        return view
    }

    interface userAccountModifyPasswordFragmentListener {
        fun onCancelModifyPasswordClick() : Unit
        fun onSaveModifyPasswordClick() : Unit
    }
}