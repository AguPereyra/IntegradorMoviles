package com.iua.agustinpereyra.view.settingsviews

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R

class SettingsMainFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_main_fragment, rootKey)

        // Get listener
        val listener = activity as MainSettingsFragmentListener

        // Set on click actions
        val settingsFilter = findPreference<Preference>("settings_filter")
        settingsFilter?.setOnPreferenceClickListener {
            listener.onFilterClick()
            true
        }
    }

    interface MainSettingsFragmentListener {
        fun onFilterClick() : Unit
    }
}