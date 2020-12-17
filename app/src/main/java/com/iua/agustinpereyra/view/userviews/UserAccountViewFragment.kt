package com.iua.agustinpereyra.view.userviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.controller.STATE_EMAIL
import com.iua.agustinpereyra.controller.STATE_USERNAME
import com.iua.agustinpereyra.controller.viewmodel.UserAccountViewModel
import com.iua.agustinpereyra.databinding.FragmentUserAccountBinding
import com.iua.agustinpereyra.view.base.ActionBarModifier


class UserAccountViewFragment : Fragment() {

    private var fragmentBinding: FragmentUserAccountBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentUserAccountBinding.inflate(inflater, container, false)

        // Get listener
        val listener = activity as UserAccountFragmentListener

        // Get viewmodel
        val accountViewModel = ViewModelProvider(this).get(UserAccountViewModel::class.java)

        // Observe current user data
        accountViewModel.getCurrentUser().observe(viewLifecycleOwner, {user ->
            fragmentBinding?.userAccountUsernameEditText?.setText(user.username)
            fragmentBinding?.userAccountEmailEditText?.setText(user.email)
        })

        // Set toolbar title and back button
        listener.setActionBarTitle(getString(R.string.user_account_title))
        listener.setActionBarHomeButtonAsUp()

        // Check if data was stored and rewrite in that case
        if (savedInstanceState != null) {
            fragmentBinding?.userAccountUsernameEditText?.setText(savedInstanceState.getString(STATE_USERNAME))
            fragmentBinding?.userAccountEmailEditText?.setText(savedInstanceState.getString(STATE_EMAIL))
        }

        // Set click listeners
        fragmentBinding?.userAccountCancelButton?.setOnClickListener{
            listener.onCancelUserAccountViewClick()
        }

        fragmentBinding?.userAccountSaveButton?.setOnClickListener{
            // Save new username, don't edit email
            val username = fragmentBinding?.userAccountUsernameEditText?.text.toString()
            accountViewModel.changeUsername(username)
            listener.onSaveUserAccountViewClick()
        }

        fragmentBinding?.userAccountPasswordEditText?.setOnTouchListener { view, _ ->
            // Navigate to the change password screen
            listener.navigateToChangePassword()
            view.performClick()
            true
        }

        return fragmentBinding?.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save username and password
        outState.run {
            putString(STATE_USERNAME, fragmentBinding?.userAccountUsernameEditText?.text.toString())
            putString(STATE_EMAIL, fragmentBinding?.userAccountEmailEditText?.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Cleaning up any references to the binding class
        fragmentBinding = null
    }

    interface UserAccountFragmentListener : ActionBarModifier {
        fun onCancelUserAccountViewClick() : Unit
        fun onSaveUserAccountViewClick() : Unit
        fun navigateToChangePassword()
    }
}