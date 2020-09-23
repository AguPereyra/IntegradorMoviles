package com.iua.agustinpereyra.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iua.agustinpereyra.R
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
            listener.onCancelClick()
        }

        view.user_account_save_button.setOnClickListener{
            listener.onSaveClick()
        }
        return view
    }

    interface userAccountFragmentListener {
        fun onCancelClick() : Unit
        fun onSaveClick() : Unit
        fun navigateToChangePassword()
    }
}