package com.iua.agustinpereyra.view.welcomeviews

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.AppBroadcastReceiver
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.databinding.ActivityWelcomeBinding
import com.iua.agustinpereyra.view.cattleviews.CattleActivity
import com.iua.agustinpereyra.view.welcomeviews.LoginFragment.LoginFragmentListener
import java.util.*


class WelcomeActivity : AppCompatActivity(), LoginFragmentListener,
    RegisterFragment.RegisterFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        // TODO: Should be here?
        // Register daily notification
        setDailyAlert()

        // Check if the user is already logged, and proceed accordingly
        val preferenceUtils = PreferenceUtils(this)
        if(preferenceUtils.isSignedIn()) {
            // Move to Main Page
            this.navigateToMainPage()
        }

        // Check whether we are re-initiating (after rotation for example) or brand-new
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

    fun setDailyAlert() {
        val intent = Intent(this, AppBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this.applicationContext, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT
        )

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 55)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}