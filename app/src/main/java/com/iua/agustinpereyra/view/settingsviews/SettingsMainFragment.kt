package com.iua.agustinpereyra.view.settingsviews

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.view.ActionBarModifier

class SettingsMainFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_main_fragment, rootKey)

        // Get listener
        val listener = activity as MainSettingsFragmentListener

        // Set up the toolbar title and back button
        listener.setActionBarTitle(getString(R.string.settings_title))
        listener.setActionBarHomeButtonAsUp()

        // Set on click actions
        val settingsFilter = findPreference<Preference>("settings_filter")
        settingsFilter?.setOnPreferenceClickListener {
            listener.onFilterClick()
            true
        }
    }

    interface MainSettingsFragmentListener : ActionBarModifier{
        fun onFilterClick() : Unit
    }
}