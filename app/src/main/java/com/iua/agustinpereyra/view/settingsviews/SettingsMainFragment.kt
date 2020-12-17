package com.iua.agustinpereyra.view.settingsviews

import android.content.SharedPreferences
import android.content.res.TypedArray
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.KEY_ORDER_BY
import com.iua.agustinpereyra.view.base.ActionBarModifier

class SettingsMainFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    // Used on updateOrderBySummary
    lateinit var orderByEntries : TypedArray
    lateinit var orderByValues : TypedArray

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

        // Initialize TypedArray variables
        orderByEntries = resources.obtainTypedArray(R.array.order_by_entries)
        orderByValues = resources.obtainTypedArray(R.array.order_by_values)

        // When starting, make sure orderBy summary is correct
        onSharedPreferenceChanged(null, KEY_ORDER_BY)
    }

    interface MainSettingsFragmentListener : ActionBarModifier {
        fun onFilterClick() : Unit
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == KEY_ORDER_BY) {
            // Update summary
            val pref = findPreference<ListPreference>(key)
            // If not null, try to update
            if (pref != null) {
                updateOrderBySummary(pref)
            }

        }
    }

    private fun updateOrderBySummary(pref : ListPreference) {
        // Get the right Summary text based on the position of value in array resource
        // Get position
        var entryPos : Int? = null
        for (i in 0 until orderByEntries.length()) {
            if (orderByValues.getString(i) == pref.value ) {
                entryPos = i
                break
            }
        }
        // Change if position found
        if (entryPos != null) {
            pref.summary = orderByEntries.getText(entryPos)
        }
    }
}