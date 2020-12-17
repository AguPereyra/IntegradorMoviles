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

        private val cattleUrls = listOf(
            "https://cdn.pixabay.com/photo/2017/09/03/12/08/cow-2710083_1280.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT16tbcR-Y5qMcU18fywrTG8i4E2RJvCO9L_w&usqp=CAU",
            "https://cdn.pixabay.com/photo/2020/10/22/10/28/cow-5675684__340.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_eX3D0ndeVerIi0Wfm1_It7Zpmk7_HEWMHA&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuf2woXswYHs7sI99zfv6EU82Y-t4gKg-GjA&usqp=CAU",
            "data:image/jpeg;base64,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",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHwAHdHVrV8GzONYkEz91ZPobrmfy1VDMiHw&usqp=CAU"
        )

        fun generateBovineUrl(): String {
            return cattleUrls[(cattleUrls.indices).random()]
        }
    }
}