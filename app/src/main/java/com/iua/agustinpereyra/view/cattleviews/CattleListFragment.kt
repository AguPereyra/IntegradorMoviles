package com.iua.agustinpereyra.view.cattleviews

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.NetworkHelper
import com.iua.agustinpereyra.controller.viewmodel.CattleViewModel
import com.iua.agustinpereyra.view.base.BaseCattleListFragment

class CattleListFragment : BaseCattleListFragment(),
            CattleCardRecyclerViewAdapter.cattleCardRecyclerViewAdapterListener{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inform that it will handle option menu icons
        setHasOptionsMenu(true)
        // Get ViewModel
        viewModel = ViewModelProvider(this).get(CattleViewModel::class.java)

        // Set the recycler view
        recyclerViewAdapter = CattleCardRecyclerViewAdapter(baseCattleList, this)

        // Initialize all that's needed
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_refresh -> {
                // Check if there is connection
                if (NetworkHelper.isNetworkConnected(context)) {
                    (viewModel as CattleViewModel).updateCattleList()
                } else {
                    listener.notifyNoInternet()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickAction(caravan: String) {
        // Delegate to Activity
        listener.navigateToSpecificBovine(caravan)
    }

    override fun onLongClickAction(caravan: String) {
        TODO("Not yet implemented")
    }
}