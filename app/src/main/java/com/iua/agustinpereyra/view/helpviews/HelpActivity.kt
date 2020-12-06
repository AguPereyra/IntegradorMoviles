package com.iua.agustinpereyra.view.helpviews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.databinding.ActivityHelpBinding
import com.iua.agustinpereyra.view.base.BaseActivity
import kotlinx.android.synthetic.main.app_simple_toolbar.view.*

class HelpActivity : BaseActivity(), HelpFragment.HelpFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        // Set toolbar
        setSupportActionBar(activityBinding.basicAppBar.simpleToolbar)

        // Set fragment dinamically
        //1. Get a reference to fragment manager
        val fragmentManager = supportFragmentManager

        //2. Start a fragment transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        //3. Add the fragment to the container
        fragmentTransaction.replace(R.id.help_fragment_layout, HelpFragment())

        //4. Commit transaction
        fragmentTransaction.commit()

    }

    override fun sendEmail () : Unit {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.setData(Uri.parse(getString(R.string.email_sendto)))
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email_intent_text)))
    }

}