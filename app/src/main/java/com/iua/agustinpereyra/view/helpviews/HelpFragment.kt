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

        // Set send email feedback action
        val contactUsEmail = findPreference<Preference>("help_contact_us_email")
        contactUsEmail?.setOnPreferenceClickListener {
            sendEmail()
            true
        }
    }

    private fun sendEmail () : Unit {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.setData(Uri.parse(getString(R.string.email_sendto)))
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email_intent_text)))
    }

}