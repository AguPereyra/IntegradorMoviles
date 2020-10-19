package com.iua.agustinpereyra.view

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.iua.agustinpereyra.R

abstract class BaseActivity : AppCompatActivity(), ActionBarModifier {

    // TODO:Check for a better way
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        // Set bar as actionbar
        val appBar = findViewById<Toolbar>(R.id.simple_toolbar)
        setSupportActionBar(appBar)
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