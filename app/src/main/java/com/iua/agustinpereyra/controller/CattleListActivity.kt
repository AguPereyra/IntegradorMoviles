package com.iua.agustinpereyra.controller

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.model.Cattle
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.cattle_list_activity.*

class CattleListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cattle_list_activity)

        // Set up the recycler
        val cattleList = generateCattleList()
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
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav_menu_account -> {
                val userAccountIntent = Intent(this, UserAccountActivity::class.java)
                startActivity(userAccountIntent)
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun generateCattleList() : List<Cattle> {
        //TODO: Move to Utils
        val cattleList = mutableListOf<Cattle>()

        // Fill with data
        for (i in 0..40) {
            val cattle = Cattle(caravan = generateRandomCaravan(), weight = (300..1000).random(), imageId = generateCattleImageId(i))
            cattleList.add(cattle)
        }
        return cattleList
    }

    private fun generateRandomCaravan() : String {
        return ('A'..'Z').random().toString() + (10..99).random() + ('A'..'Z').random().toString()
    }

    private fun generateCattleImageId(run: Int) : Int {
        when (run % 4) {
            0 -> return R.drawable.sample_cow_1
            1 -> return R.drawable.sample_cow_2
            2 -> return R.drawable.sample_cow_3
            3 -> return R.drawable.sample_cow_4
        }
        return R.drawable.sample_cow_1
    }
}