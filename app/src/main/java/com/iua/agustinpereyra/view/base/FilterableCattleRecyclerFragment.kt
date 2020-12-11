package com.iua.agustinpereyra.view.base

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.view.cattleviews.CattleCardRecyclerViewAdapter

/**
* FilterableCattleRecyclerFragment is a base fragment that contains needed logic
* to filter, order and search cattle over a list that should be showed over a RecyclerViewAdapter.
* Classes that inherit from it should initialize variables baseCattleList, currentCattleList and
* recyclerView on onCreateView method.
* */
abstract class FilterableCattleRecyclerFragment : Fragment(), SearchView.OnQueryTextListener {

    // Base list of data
    protected var baseCattleList : List<Cattle> = listOf()
    // Currently showing list of data, used for searches
    protected lateinit var currentCattleList : List<Cattle>
    protected lateinit var recyclerViewAdapter : CattleCardRecyclerViewAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_top_app_bar, menu)

        // Set this fragment as the QueryTextListener
        val searchItem = menu.findItem(R.id.main_top_app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        // Set searchable hint
        searchView.queryHint = getString(R.string.search_hint)
    }


    override fun onResume() {
        super.onResume()
        val cattleList = CattleManager.orderBasedOnPreferences(baseCattleList, context)
        currentCattleList = cattleList
        recyclerViewAdapter.setCattle(cattleList)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // Don't do anything special
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // Search if something is to be searched
        if (newText != null) {
            val searchedList = searchItem(newText)
            recyclerViewAdapter.setCattle(searchedList)
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

}