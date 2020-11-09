package com.iua.agustinpereyra.view.cattleviews

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.*
import com.iua.agustinpereyra.controller.viewmodel.CattleViewModel
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.view.base.ActionBarModifier
import com.iua.agustinpereyra.view.base.FilterableCattleRecyclerFragment

class CattleListFragment : Fragment(){

    private lateinit var cattleViewModel: CattleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cattle_list, container, false)

        // Inform that it will handle option menu icons
        setHasOptionsMenu(true)

        // Get ViewModel
        cattleViewModel = ViewModelProvider(this).get(CattleViewModel::class.java)

        // Set up the recycler
        val baseCattleList = listOf<Cattle>()
        val currentCattleList = baseCattleList
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = CattleCardRecyclerViewAdapter(baseCattleList)

        val recyclerView = view.findViewById<RecyclerView>(R.id.cattle_list_recycler)
        recyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // Observe
        cattleViewModel.getCattleList().observe(viewLifecycleOwner, Observer { newCattle ->
            viewAdapter.setCattle(newCattle)
        })

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