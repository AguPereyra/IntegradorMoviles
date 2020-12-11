package com.iua.agustinpereyra.view.cattleviews

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.NetworkHelper
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.controller.viewmodel.CattleViewModel
import com.iua.agustinpereyra.controller.viewmodel.MonitoredCattleViewModel
import com.iua.agustinpereyra.view.base.BaseCattleListFragment
import java.lang.Error

class MonitoredCattleListFragment : BaseCattleListFragment(),
    CattleCardRecyclerViewAdapter.cattleCardRecyclerViewAdapterListener{

    private var currentUser: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get preference utils, used to get the current user
        val preferenceUtils = PreferenceUtils(context)
        // Get the current user
        currentUser = preferenceUtils.getCurrentUser()
        if (currentUser == null) {
            // Fail, something went wrong, there should be a user logged
            throw Error("Error: While trying to get current user at MonitoredCattleListFragment. Current user not found.")
        }

        // Inform that it will handle option menu icons
        setHasOptionsMenu(true)
        // Get ViewModel and get data
        viewModel = ViewModelProvider(this).get(MonitoredCattleViewModel::class.java)
        (viewModel as MonitoredCattleViewModel).setMonitoredCattleListOfUser(currentUser as Int)

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
                    if (currentUser != null) {
                        (viewModel as MonitoredCattleViewModel).updateCattleList(currentUser as Int)
                    }
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