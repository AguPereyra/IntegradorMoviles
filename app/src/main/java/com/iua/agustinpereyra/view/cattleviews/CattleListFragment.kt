package com.iua.agustinpereyra.view.cattleviews

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.NetworkHelper
import com.iua.agustinpereyra.controller.viewmodel.CattleViewModel
import com.iua.agustinpereyra.databinding.FragmentCattleListBinding
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.view.base.ActionBarModifier
import com.iua.agustinpereyra.view.base.FilterableCattleRecyclerFragment

class CattleListFragment : FilterableCattleRecyclerFragment(){

    private lateinit var cattleViewModel: CattleViewModel
    private lateinit var listener: CattleListFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentCattleListBinding.inflate(inflater, container, false)

        // Inform that it will handle option menu icons
        setHasOptionsMenu(true)

        // Get ViewModel
        cattleViewModel = ViewModelProvider(this).get(CattleViewModel::class.java)

        // Set up the recycler
        baseCattleList = listOf<Cattle>()
        currentCattleList = baseCattleList
        val viewManager = LinearLayoutManager(context)
        recyclerViewAdapter = CattleCardRecyclerViewAdapter(baseCattleList)

        val recyclerView = fragmentBinding.cattleListRecycler
        recyclerView.apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }

        // Observe and update UI and local variable
        cattleViewModel.cattleList.observe(viewLifecycleOwner, Observer { newCattle ->
            baseCattleList = newCattle
            recyclerViewAdapter.setCattle(newCattle)
        })

        // Get Activity with needed functions
        listener = activity as CattleListFragmentListener

        // Set up the toolbar corresponding title
        listener.setActionBarTitle(getString(R.string.cattle_list_title))

        // Check for Internet and notify no conection available
        if (!NetworkHelper.isNetworkConnected(context)) {
            listener.notifyNoInternet()
        }

        return fragmentBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_refresh -> {
                // Check if there is connection
                if (NetworkHelper.isNetworkConnected(context)) {
                    cattleViewModel.updateCattleList()
                } else {
                    listener.notifyNoInternet()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    interface CattleListFragmentListener : ActionBarModifier {
        fun navigateToSpecificBovine() : Unit
        fun notifyNoInternet()
    }
}