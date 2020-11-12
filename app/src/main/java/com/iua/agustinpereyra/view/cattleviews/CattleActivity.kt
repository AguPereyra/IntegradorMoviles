package com.iua.agustinpereyra.view.cattleviews

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.view.helpviews.HelpActivity
import com.iua.agustinpereyra.view.settingsviews.SettingsActivity
import com.iua.agustinpereyra.view.userviews.UserAccountActivity
import com.iua.agustinpereyra.controller.VIEW_USER_REQUEST
import com.iua.agustinpereyra.view.NotificationGenerator
import kotlinx.android.synthetic.main.activity_cattle.*
import kotlinx.android.synthetic.main.app_main_toolbar.*

class CattleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CattleListFragment.CattleListFragmentListener {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cattle)

        // Set toolbar as support action bar
        setSupportActionBar(main_toolbar)

        // Tie together drawer layout and action bar
        toggle = ActionBarDrawerToggle(this, drawer_layout, main_toolbar, 0, 0)
        drawer_layout.addDrawerListener(toggle)


        // Set item click listener
        navigation_view.setNavigationItemSelectedListener(this)


        // Check whether we are re-initiating (after rotation for example) or brand-new
        // and add fragment if needed
        if (savedInstanceState == null) {
            // Set fragment dinamically
            //1. Get a reference to fragment manager
            val fragmentManager = supportFragmentManager

            //2. Start a fragment transaction
            val fragmentTransaction = fragmentManager.beginTransaction()

            //3. Add the fragment to the container
            fragmentTransaction.replace(
                R.id.cattle_fragment_layout,
                CattleListFragment()
            )

            //4. Commit transaction
            fragmentTransaction.commit()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    // Navigation logic inside navbar
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav_menu_account -> {
                val userAccountIntent = Intent(this, UserAccountActivity::class.java)
                startActivityForResult(userAccountIntent, VIEW_USER_REQUEST)
            }

            R.id.nav_menu_preferences -> {
                val settingsActivityIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsActivityIntent)
            }

            R.id.nav_menu_help -> {
                val helpActivityIntent = Intent(this, HelpActivity::class.java)
                startActivity(helpActivityIntent)
            }
        }
        return true
    }

    // Set the navbar if menu is clicked or handle as appropriate
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // Close navbar if open on back pressed
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    // ActionBarModifier functions
    override fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setActionBarHomeButtonAsUp() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun notifyNoInternet() {
        NotificationGenerator.noInternetConnectionAlert(this)
    }

    // Cattle List needed functions
    override fun navigateToSpecificBovine() {
        TODO("Not yet implemented")
    }

}