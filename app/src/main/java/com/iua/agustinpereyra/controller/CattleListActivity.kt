package com.iua.agustinpereyra.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.model.Cattle
import kotlinx.android.synthetic.main.cattle_list_activity.*

class CattleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cattle_list_activity)

        // Set up the recycler
        val cattleList = generateCattleList()
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = CattleCardRecyclerViewAdapter(cattleList)

        cattle_list_recycler.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun generateCattleList() : List<Cattle> {
        val cattleList = mutableListOf<Cattle>()

        // Fill with data
        for (i in 0..40) {
            val cattle = Cattle(caravan = generateRandomCaravan(), weight = (300..1000).random(), imageId = generateCattleImageId(i))
            cattleList.add(cattle)
        }
        return cattleList
    }

    private fun generateRandomCaravan() : String {
        return ('A'..'Z').random().toString() + (10..99).random() + ('A'..'Z').random().toString()
    }

    private fun generateCattleImageId(run: Int) : Int {
        when (run % 4) {
            0 -> return R.drawable.sample_cow_1
            1 -> return R.drawable.sample_cow_2
            2 -> return R.drawable.sample_cow_3
            3 -> return R.drawable.sample_cow_4
        }
        return R.drawable.sample_cow_1
    }
}