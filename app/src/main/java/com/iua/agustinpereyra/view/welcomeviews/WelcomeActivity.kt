package com.iua.agustinpereyra.view.welcomeviews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.view.cattleviews.CattleActivity
import com.iua.agustinpereyra.view.welcomeviews.LoginFragment.LoginFragmentListener

class WelcomeActivity : AppCompatActivity(), LoginFragmentListener,
    RegisterFragment.RegisterFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Check whether we are re-initiating (after rotaion for example) or brand-new
        if (savedInstanceState == null) {
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

    }

    override fun navigateToMainPage() {
        // Get username (TODO: Change this when persistence is implemented)
        val mainPageIntent = Intent(this, CattleActivity::class.java)
        startActivity(mainPageIntent)
        // Not returning here
        finish()
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