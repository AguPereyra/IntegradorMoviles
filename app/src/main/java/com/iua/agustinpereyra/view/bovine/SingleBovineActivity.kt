package com.iua.agustinpereyra.view.bovine

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.controller.CARAVAN
import com.iua.agustinpereyra.controller.StaticDataGenerator
import com.iua.agustinpereyra.databinding.ActivitySingleBovineBinding
import com.iua.agustinpereyra.view.base.BaseActivity

class SingleBovineActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Check if parameters were passed through Intents
        val callingIntent: Intent = intent
        super.onCreate(savedInstanceState)
        val singleBovineBinding = ActivitySingleBovineBinding.inflate(layoutInflater)
        setContentView(singleBovineBinding.root)

        // Set Action Bar and Title
        setSupportActionBar(singleBovineBinding.basicAppBar.simpleToolbar)
        setActionBarTitle(getString(R.string.bovine_x, callingIntent.getStringExtra(CARAVAN)))
        setActionBarHomeButtonAsUp()

        // Set Recycler View
        val bovineDataList = StaticDataGenerator.generateBovineData(this)
        val viewManager = LinearLayoutManager(this)
        val recyclerViewAdapter = BovineDataRecyclerViewAdapter(bovineDataList)

        singleBovineBinding.bovineDataRecyclerView.apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }
    }
}