package com.iua.agustinpereyra.view.base

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.repository.database.model.Cattle
import com.iua.agustinpereyra.view.cattleviews.CattleCardRecyclerViewAdapter

/*
* FilterableCattleRecyclerFragment is a base fragment that contains needed logic
* to filter, order and search cattle over a list that should be showed over a RecyclerViewAdapter.
* Classes that inherit from it should initialize variables baseCattleList, currentCattleList and
* recyclerView on onCreateView method.
* */
abstract class FilterableCattleRecyclerFragment : Fragment(), SearchView.OnQueryTextListener {

    // Base list of data
    protected lateinit var baseCattleList : List<Cattle>
    // Currently showing list of data, used for searches
    protected lateinit var currentCattleList : List<Cattle>
    protected lateinit var recyclerView : RecyclerView

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
        // TODO: Is it okay to put it here or should go at controller?
        var cattleList = baseCattleList
        val preferenceUtils = PreferenceUtils(context)

        // Filtering
        // Check sex filter
        val sexFilterStatus = preferenceUtils.getSexFilter()
        if (sexFilterStatus != null) {
            cattleList = CattleManager.filterListBySex(sexFilterStatus, cattleList)
        }

        // Check weight filter
        val weightFilterStatus = preferenceUtils.getWeightFilter()
        if (weightFilterStatus != null) {
            cattleList = CattleManager.filterListByWeight(weightFilterStatus, cattleList)
        }

        // Order resulting list
        val orderCriteria = preferenceUtils.getOrderBy()
        // Reorder list
        cattleList = CattleManager.orderListBy(orderCriteria, cattleList)

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

}