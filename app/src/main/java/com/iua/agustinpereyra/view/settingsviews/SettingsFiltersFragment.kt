package com.iua.agustinpereyra.view.settingsviews

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R

class SettingsFiltersFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_filters_fragment, rootKey)
    }
}