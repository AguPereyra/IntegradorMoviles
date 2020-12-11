package com.iua.agustinpereyra.controller

import android.content.Context
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.model.BovineDataField
import com.iua.agustinpereyra.repository.database.entities.Cattle

class StaticDataGenerator {
    companion object {
        fun generateCattleList() : List<Cattle> {
            val cattleList = mutableListOf<Cattle>()

            // Fill with data
            for (i in 0..40) {
                val cattle = Cattle( caravan = generateRandomCaravan(), weight = (300..1000).random(), "https://dailyspreaddotcom.files.wordpress.com/2013/07/cow_face-e1373197790378.jpg",i%2 == 0)
                cattleList.add(cattle)
            }
            return cattleList
        }

        fun generateRandomCaravan() : String {
            return ('A'..'Z').random().toString() + (10..99).random() + ('A'..'Z').random().toString()
        }

        fun generateCattleImageId(run: Int) : Int {
            when (run % 4) {
                0 -> return R.drawable.sample_cow_1
                1 -> return R.drawable.sample_cow_2
                2 -> return R.drawable.sample_cow_3
                3 -> return R.drawable.sample_cow_4
            }
            return R.drawable.sample_cow_1
        }

        fun generateBovineData(context: Context): List<BovineDataField> {
            val genericData = BovineDataField(context.getString(R.string.generic_title_1),
                                                context.getString(R.string.generic_field_1),
                                                context.getString(R.string.generic_field_2))
            val healthData = BovineDataField(context.getString(R.string.generic_title_2),
                                                context.getString(R.string.generic_field_3),
                                                context.getString(R.string.generic_field_4))
            return listOf(genericData, healthData)
        }
    }
}