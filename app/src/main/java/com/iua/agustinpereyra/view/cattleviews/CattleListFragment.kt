package com.iua.agustinpereyra.view.cattleviews

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.view.base.ActionBarModifier
import com.iua.agustinpereyra.view.base.FilterableCattleRecyclerFragment

class CattleListFragment : FilterableCattleRecyclerFragment(){

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

    interface CattleListFragmentListener : ActionBarModifier {
        fun navigateToSpecificBovine() : Unit
    }
}