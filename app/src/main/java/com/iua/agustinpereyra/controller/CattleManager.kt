package com.iua.agustinpereyra.controller

import android.content.Context
import com.iua.agustinpereyra.repository.database.entities.Cattle

class CattleManager {
    companion object {
        fun filterListBySex(sex : String, cattleList : List<Cattle>) : List<Cattle>{
            return when (sex) {
                MALE -> cattleList.filter { it.sex }
                else -> cattleList.filter { !it.sex }
            }
        }

        fun filterListByWeight(maxWeight : Int, cattleList: List<Cattle>) : List<Cattle> {
            return cattleList.filter { it.weight < maxWeight }
        }

        fun orderListBy(orderCriteria : String, cattleList: List<Cattle>) : List<Cattle> {
            return when (orderCriteria) {
                ORDER_BY_SEX -> cattleList.sortedBy { it.sex }
                ORDER_BY_WEIGHT_ASC -> cattleList.sortedBy { it.weight }
                ORDER_BY_WEIGHT_DESC -> cattleList.sortedByDescending { it.weight }
                else -> cattleList
            }
        }

        fun orderBasedOnPreferences(originalList: List<Cattle>, context: Context?): List<Cattle> {
            var cattleList = originalList
            val preferenceUtils = PreferenceUtils(context)
            // Filtering
            // Check sex filter
            val sexFilterStatus = preferenceUtils.getSexFilter()
            if (sexFilterStatus != null) {
                cattleList = CattleManager.filterListBySex(sexFilterStatus, cattleList)
            }

            // Check weight filter
            val weightFilterStatus = preferenceUtils.getWeightFilter()
            if (weightFilterStatus != null) {
                cattleList = CattleManager.filterListByWeight(weightFilterStatus, cattleList)
            }

            // Order resulting list
            val orderCriteria = preferenceUtils.getOrderBy()
            // Reorder list
            cattleList = CattleManager.orderListBy(orderCriteria, cattleList)

            return cattleList
        }
    }
}