package com.iua.agustinpereyra.view.cattleviews

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.NetworkHelper
import com.iua.agustinpereyra.controller.PreferenceUtils
import com.iua.agustinpereyra.controller.viewmodel.CattleViewModel
import com.iua.agustinpereyra.view.base.BaseCattleListFragment

class CattleListFragment : BaseCattleListFragment(),
            CattleCardRecyclerViewAdapter.cattleCardRecyclerViewAdapterListener{

    private var currentUser: Int? = null

    // Variable used to keep the list of selected cattle
    private val selectedCaravans = mutableListOf<String>()

    /**
     * actionMode is a utility variable that helps to follow the state of
     * the action mode
     */
    private var actionMode: ActionMode? = null

    /**
     * actionModeCallback implements the ActionMode.Callback interface
     * with the needed logic for the Contextual Action Mode to work
     */
    private val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate the menu resource with the context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.bovine_all_menu, menu)
            return true
        }

        // Called each time the action mode is shown, always called after onCreateActionMode
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.context_menu_monitor -> {
                    // Call the ViewModel with the list of caravans and the user id
                    if (currentUser != null) {
                        (viewModel as CattleViewModel).addToMonitored(currentUser as Int, selectedCaravans)
                        mode.finish() // Close Contextual Menu
                        true
                    } else {
                        throw Error("Error: While trying to get current user at MonitoredCattleListFragment. Current user not found.")
                    }
                }
                else -> false
            }
        }

        // Called when the users leaves the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }

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
            throw java.lang.Error("Error: While trying to get current user at MonitoredCattleListFragment. Current user not found.")
        }

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
        // Check if the caravan is being selected or deselected
        if (selectedCaravans.contains(caravan)) {
            // Deselecting
            selectedCaravans.remove(caravan)
            // Close if not selected cattle remaining
            if (selectedCaravans.size == 0) {
                actionMode?.finish()
            }
        } else {
            // Selecting
            selectedCaravans.add(caravan)
            // Check if actionMode is not already on screen
            when (actionMode) {
                null -> {
                    // Initiate the context action mode
                    actionMode = activity?.startActionMode(actionModeCallback)
                }
            }
        }
    }
}