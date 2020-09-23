package com.iua.agustinpereyra.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.LoginFragment.loginFragmentListener
import com.iua.agustinpereyra.utils.USERNAME
import kotlinx.android.synthetic.main.login_fragment.*

class WelcomeActivity : AppCompatActivity(), loginFragmentListener, RegisterFragment.registerFragmentListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)

        // Set fragment dinamically
        //1. Get a reference to fragment manager
        val fragmentManager = supportFragmentManager

        //2. Start a fragment transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        //3. Add the fragment to the container
        fragmentTransaction.replace(R.id.welcome_fragment_layout, LoginFragment())

        //4. Commit transaction
        fragmentTransaction.commit()

    }

    override fun navigateToMainPage() {
        // Get username (TODO: Change this when persistence is implemented)
        val username = username_input_edit_text.text
        val mainPageIntent = Intent(this, CattleListActivity::class.java)
        mainPageIntent.putExtra(USERNAME, username)
        startActivity(mainPageIntent)
    }

    override fun nagivateToRegisterPage() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val registerFragment = RegisterFragment()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.welcome_fragment_layout, registerFragment)
        fragmentTransaction.commit()

    }

    override fun nagivateToLoginPage() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        fragmentTransaction.replace(R.id.welcome_fragment_layout, loginFragment)
        fragmentTransaction.commit()
    }
}