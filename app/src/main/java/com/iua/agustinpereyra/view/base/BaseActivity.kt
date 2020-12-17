package com.iua.agustinpereyra.view.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.iua.agustinpereyra.databinding.AppSimpleToolbarBinding

abstract class BaseActivity : AppCompatActivity(), ActionBarModifier {

    // ActionBarModifier functions
    override fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setActionBarHomeButtonAsUp() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Used to go back in terms of fragments or activities
    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0) {
            // Go back with fragments
            supportFragmentManager.popBackStack()
        } else {
            // Go back with  activities
            finish()
        }
        return true
    }
}