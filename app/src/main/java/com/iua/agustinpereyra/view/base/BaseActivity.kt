package com.iua.agustinpereyra.view.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.databinding.AppMainToolbarBinding
import com.iua.agustinpereyra.databinding.AppSimpleToolbarBinding

abstract class BaseActivity : AppCompatActivity(), ActionBarModifier {

    // TODO:Check for a better way
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        // Set bar as actionbar
        val appBar = AppSimpleToolbarBinding.inflate(layoutInflater)
        setSupportActionBar(appBar.simpleToolbar)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        // Set bar as actionbar
        val appBar = AppSimpleToolbarBinding.inflate(layoutInflater)
        setSupportActionBar(appBar.simpleToolbar)
    }

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