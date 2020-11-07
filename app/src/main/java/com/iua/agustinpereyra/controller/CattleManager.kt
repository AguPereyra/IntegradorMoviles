package com.iua.agustinpereyra.controller

import com.iua.agustinpereyra.model.Cattle

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
    }
}