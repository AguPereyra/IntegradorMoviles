package com.iua.agustinpereyra.view.helpviews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.iua.agustinpereyra.R

class HelpFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.help_page, rootKey)

        // Get listener from parent
        val listener = activity as HelpFragmentListener

        // Set send email feedback action
        val contactUsEmail = findPreference<Preference>("help_contact_us_email")
        contactUsEmail?.setOnPreferenceClickListener {
            listener.sendEmail()
            true
        }
    }

    interface HelpFragmentListener {
        fun sendEmail() : Unit
    }

}