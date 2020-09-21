package com.iua.agustinpereyra.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.LoginFragment.loginFragmentListener
import com.iua.agustinpereyra.utils.USERNAME
import kotlinx.android.synthetic.main.login_fragment.*

class WelcomeActivity : AppCompatActivity(), loginFragmentListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)
    }

    override fun navigateToMainPage() {
        // Get username (TODO: Change this when persistence is implemented)
        val username = username_input_edit_text.text
        val mainPageIntent = Intent(this, CattleListActivity::class.java)
        mainPageIntent.putExtra(USERNAME, username)
        startActivity(mainPageIntent)
        // TODO: Check why fragment is not showing!
    }
}