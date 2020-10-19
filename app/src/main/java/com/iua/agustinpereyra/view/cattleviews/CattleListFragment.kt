package com.iua.agustinpereyra.view.cattleviews

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.StaticDataGenerator
import com.iua.agustinpereyra.controller.VIEW_USER_REQUEST
import com.iua.agustinpereyra.view.ActionBarModifier
import com.iua.agustinpereyra.view.helpviews.HelpActivity
import com.iua.agustinpereyra.view.settingsviews.SettingsActivity
import com.iua.agustinpereyra.view.userviews.UserAccountActivity
import kotlinx.android.synthetic.main.app_main_toolbar.*
import kotlinx.android.synthetic.main.fragment_cattle_list.*

class CattleListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cattle_list, container, false)

        // Set up the recycler
        val cattleList = StaticDataGenerator.generateCattleList()
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = CattleCardRecyclerViewAdapter(cattleList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.cattle_list_recycler)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // Get Activity with needed functions
        val listener = activity as CattleListFragmentListener

        // Set up the toolbar corresponding title
        listener.setActionBarTitle(getString(R.string.cattle_list_title))

        return view
    }

    interface CattleListFragmentListener : ActionBarModifier {
        fun navigateToSpecificBovine() : Unit
    }
}