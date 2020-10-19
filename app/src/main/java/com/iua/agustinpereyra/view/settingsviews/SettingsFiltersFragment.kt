package com.iua.agustinpereyra.view.settingsviews

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.view.ActionBarModifier

class SettingsFiltersFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_filters_fragment, rootKey)

        // Get listener
        val listener = activity as SettingsFilterFragmentListener

        // Set up the toolbar title and back button
        listener.setActionBarTitle(getString(R.string.settings_filter_title))
        listener.setActionBarHomeButtonAsUp()
    }

    interface SettingsFilterFragmentListener : ActionBarModifier
}