package com.iua.agustinpereyra.view.settingsviews

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.KEY_ORDER_BY
import com.iua.agustinpereyra.controller.PREF_ORDER_BY_DEF
import com.iua.agustinpereyra.view.ActionBarModifier

class SettingsMainFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

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

        // Subscribe to preferences listener
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    interface MainSettingsFragmentListener : ActionBarModifier{
        fun onFilterClick() : Unit
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == KEY_ORDER_BY) {
            // Update summary
            val pref = findPreference<ListPreference>(key)
            //TODO: Make it with array values
            pref?.summary = pref?.value
        }
    }
}