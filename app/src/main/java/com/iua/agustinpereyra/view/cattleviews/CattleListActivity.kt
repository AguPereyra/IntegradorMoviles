package com.iua.agustinpereyra.view.cattleviews

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.StaticDataGenerator
import com.iua.agustinpereyra.view.helpviews.HelpActivity
import com.iua.agustinpereyra.view.settingsviews.SettingsActivity
import com.iua.agustinpereyra.view.userviews.UserAccountActivity
import com.iua.agustinpereyra.model.Cattle
import com.iua.agustinpereyra.controller.VIEW_USER_REQUEST
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.cattle_list_activity.*

class CattleListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Check why navbar is not showing elsewhere
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cattle_list_activity)

        // Set up the recycler
        val cattleList = StaticDataGenerator.generateCattleList()
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = CattleCardRecyclerViewAdapter(cattleList)

        cattle_list_recycler.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // Add toolbar
        setSupportActionBar(app_bar)

        // Tie together drawer layout and action bar
        val toggle = ActionBarDrawerToggle(this, drawer_layout, app_bar, 0, 0)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        // Set item click listener
        navigation_view.setNavigationItemSelectedListener(this)

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        cattle_list_recycler.addItemDecoration(dividerItemDecoration)
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

    // Set the navbar if menu is clicked
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
}