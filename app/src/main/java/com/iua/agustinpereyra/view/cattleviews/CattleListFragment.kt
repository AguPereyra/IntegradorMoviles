package com.iua.agustinpereyra.view.cattleviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.StaticDataGenerator

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

        return view
    }

    interface CattleListFragmentListener {
        fun navigateToSpecificBovine() : Unit
    }
}