package com.iua.agustinpereyra.view.helpviews

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.view.base.ActionBarModifier

class HelpFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.help_page, rootKey)

        // Get listener from parent
        val listener = activity as HelpFragmentListener

        // Set up the toolbar title and back button
        listener.setActionBarTitle(getString(R.string.help_title))
        listener.setActionBarHomeButtonAsUp()

        // Set send email feedback action
        val contactUsEmail = findPreference<Preference>("help_contact_us_email")
        contactUsEmail?.setOnPreferenceClickListener {
            listener.sendEmail()
            true
        }
    }

    interface HelpFragmentListener : ActionBarModifier {
        fun sendEmail() : Unit
    }

}