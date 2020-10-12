package com.iua.agustinpereyra.view.settingsviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iua.agustinpereyra.R

class SettingsActivity : AppCompatActivity(), SettingsMainFragment.MainSettingsFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        // Set fragment dinamically
        //1. Get a reference to fragment manager
        val fragmentManager = supportFragmentManager

        //2. Start a fragment transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        //3. Add the fragment to the container
        fragmentTransaction.replace(R.id.settings_fragment_layout, SettingsMainFragment())

        //4. Commit transaction
        fragmentTransaction.commit()

    }

    override fun onFilterClick() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val settingsFilterFragment = SettingsFiltersFragment()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.settings_fragment_layout, settingsFilterFragment)
        fragmentTransaction.commit()
    }
}