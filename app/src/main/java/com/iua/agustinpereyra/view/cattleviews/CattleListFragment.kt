package com.iua.agustinpereyra.view.cattleviews

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.model.Cattle
import com.iua.agustinpereyra.view.ActionBarModifier
import com.iua.agustinpereyra.view.helpviews.HelpActivity
import com.iua.agustinpereyra.view.settingsviews.SettingsActivity
import com.iua.agustinpereyra.view.userviews.UserAccountActivity
import kotlinx.android.synthetic.main.app_main_toolbar.*
import kotlinx.android.synthetic.main.fragment_cattle_list.*

class CattleListFragment : Fragment(), SearchView.OnQueryTextListener{

    // Base list of data
    private lateinit var baseCattleList : List<Cattle>
    // Currently showing list of data
    private lateinit var currentCattleList : List<Cattle>
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cattle_list, container, false)

        // Inform that it will handle option menu icons
        setHasOptionsMenu(true)

        // Set up the recycler
        baseCattleList = StaticDataGenerator.generateCattleList()
        currentCattleList = baseCattleList
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = CattleCardRecyclerViewAdapter(baseCattleList)

        recyclerView = view.findViewById<RecyclerView>(R.id.cattle_list_recycler)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_top_app_bar, menu)

        // Set this fragment as the QueryTextListener
        val searchItem = menu.findItem(R.id.main_top_app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }


    override fun onResume() {
        super.onResume()
        // TODO: Is it okay to put it here or should go at controller?
        var cattleList = baseCattleList
        val preferenceUtils = PreferenceUtils(context)

        // Filtering
        // Check sex filter
        val sexFilterStatus = preferenceUtils.getSexFilter()
        if (sexFilterStatus != null) {
            cattleList = when (sexFilterStatus) {
                MALE -> cattleList.filter { it.sex }
                else -> cattleList.filter { !it.sex }
            }
        }

        // Check weight filter
        val weightFilterStatus = preferenceUtils.getWeightFilter()
        if (weightFilterStatus != null) {
            cattleList = cattleList.filter { it.weight < weightFilterStatus }
        }

        // Order resulting list
        val orderCriteria = preferenceUtils.getOrderBy()
        // Reorder list
        cattleList = when (orderCriteria) {
            ORDER_BY_SEX -> cattleList.sortedBy { it.sex }
            ORDER_BY_WEIGHT_ASC -> cattleList.sortedBy { it.weight }
            ORDER_BY_WEIGHT_DESC -> cattleList.sortedByDescending { it.weight }
            else -> cattleList
        }

        // Swap to new adapter with updated data
        //  TODO: Is this optimal? Maybe a method to insert and pop all data in Adapter is better
        currentCattleList = cattleList
        val newAdapter = CattleCardRecyclerViewAdapter(cattleList)
        recyclerView.swapAdapter(newAdapter, true)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // Don't do anything special
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // Search if something is to be searched
        if (newText != null) {
            val searchedList = searchItem(newText)
            val newAdapter = CattleCardRecyclerViewAdapter(searchedList)
            recyclerView.swapAdapter(newAdapter, true)
        }
        return true
    }

    private fun searchItem(text : String) : List<Cattle> {
        // TODO: Should go here?
        val searchedList = mutableListOf<Cattle>()
        for (bovine in currentCattleList) {
            if (bovine.caravan.contains(text, ignoreCase = true)) {
                searchedList.add(bovine)
            }
        }
        return searchedList
    }

    interface CattleListFragmentListener : ActionBarModifier {
        fun navigateToSpecificBovine() : Unit
    }
}