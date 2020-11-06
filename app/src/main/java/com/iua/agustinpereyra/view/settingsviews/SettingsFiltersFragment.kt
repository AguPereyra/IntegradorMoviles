package com.iua.agustinpereyra.view.settingsviews

import android.content.SharedPreferences
import android.content.res.TypedArray
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.KEY_SEX_FILTER
import com.iua.agustinpereyra.view.ActionBarModifier

class SettingsFiltersFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    // Used on updateSexFilterTitle
    lateinit var sexFilterEntries : TypedArray
    lateinit var sexFilterValues : TypedArray

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_filters_fragment, rootKey)

        // Get listener
        val listener = activity as SettingsFilterFragmentListener

        // Set up the toolbar title and back button
        listener.setActionBarTitle(getString(R.string.settings_filter_title))
        listener.setActionBarHomeButtonAsUp()

        // Initialize Filter values and entries
        sexFilterEntries = resources.obtainTypedArray(R.array.filter_by_entries)
        sexFilterValues = resources.obtainTypedArray(R.array.filter_by_values)

        // When starting make sure sexFilter summary is correct
        onSharedPreferenceChanged(null, KEY_SEX_FILTER)

        // Subscribe to be informed when preferences chage
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    interface SettingsFilterFragmentListener : ActionBarModifier

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        // Check if sex filter was changed
        if (key == KEY_SEX_FILTER) {
            val pref = findPreference<ListPreference>(key)
            if (pref != null) {
                updateSexFilterTitle(pref)
            }
        }
    }

    private fun updateSexFilterTitle(pref : ListPreference) {
        // Get the right Summary text based on the position of value in array resource
        // Get position
        var entryPos : Int? = null
        for (i in 0..sexFilterEntries.length()) {
            if (sexFilterValues.getString(i) == pref.value ) {
                entryPos = i
                break
            }
        }
        // Change if position found
        if (entryPos != null) {
            pref.title = sexFilterEntries.getText(entryPos)
        }
    }
}