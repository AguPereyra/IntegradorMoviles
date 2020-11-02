package com.iua.agustinpereyra.controller

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.iua.agustinpereyra.R

class PreferenceUtils {
    private lateinit var context : Context
    private lateinit var defaultSharedPreferences : SharedPreferences

    constructor(context: Context) {
        this.context = context
        this.defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    public fun getOrderBy() : String {
        val orderByKey = this.context.getString(R.string.order_by_settings_key)
        val pref = defaultSharedPreferences.getString(orderByKey, PREF_ORDER_BY_DEF)
        if (pref == null) {
            return PREF_ORDER_BY_DEF
        } else {
            return pref
        }
    }

    /*
    * Returns: String if set, null otherwise
    * */
    public fun getSexFilter() : String? {
        val sexFilterSwitch = this.context.getString(R.string.sex_filter_switch_key)
        val switchOn = defaultSharedPreferences.getBoolean(sexFilterSwitch, PREF_DEF_SWITCH)
        if (switchOn) {
            val sexFilter = this.context.getString(R.string.sex_filter_list_key)
            val pref = defaultSharedPreferences.getString(sexFilter, PREF_SEX_FILTER_DEF)
            if (pref == null) {
                return PREF_SEX_FILTER_DEF
            } else {
                return pref
            }
        } else {
            return null
        }
    }

    /*
    * Returns: String if set, null otherwise
    * */
    //TODO: Improve usability by adding two bars or something else
    public fun getWeightFilter() : Int? {
        val weightFilterSwitch = this.context.getString(R.string.weight_filter_switch_key)
        val switchOn = defaultSharedPreferences.getBoolean(weightFilterSwitch, PREF_DEF_SWITCH)
        if (switchOn) {
            val weightFilter = this.context.getString(R.string.weight_filter_value_key)
            val pref = defaultSharedPreferences.getInt(weightFilter, PREF_DEF_WEIGHT)
            if (pref == null) {
                return PREF_DEF_WEIGHT
            } else {
                return pref
            }
        } else {
            return null
        }
    }
}