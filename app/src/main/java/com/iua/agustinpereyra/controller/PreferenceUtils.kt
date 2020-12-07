package com.iua.agustinpereyra.controller

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.iua.agustinpereyra.R
import java.lang.NullPointerException

//TODO: Analyze if it is not better as Singleton
class PreferenceUtils {

    private var context : Context
    private var defaultSharedPreferences : SharedPreferences
    private var ownSharedPreferences : SharedPreferences

    constructor(context: Context?) {
        //TODO: Improve exeption handling
        if (context == null) {
            throw NullPointerException("Context passed to PrecerenceUtils shouldn't be null")
        }
        this.context = context
        this.defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        this.ownSharedPreferences = context.getSharedPreferences(OWN_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getOrderBy() : String {
        val orderByKey = this.context.getString(R.string.order_by_settings_key)
        val pref = defaultSharedPreferences.getString(orderByKey, PREF_ORDER_BY_DEF)
        if (pref == null) {
            return PREF_ORDER_BY_DEF
        } else {
            return pref
        }
    }

    /**
    * Returns: String if set, null otherwise
    * */
    fun getSexFilter() : String? {
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

    /**
    * Returns: String if set, null otherwise
    * */
    //TODO: Improve usability by adding two bars or something else
    fun getWeightFilter() : Int? {
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

    /**
    * saveCurrentUser saves the ID of the currently logged user
    */
    fun saveCurrentUser(userId: Int) {
        ownSharedPreferences.edit().putInt(CURRENT_USER_ID, userId).apply()
    }

    /**
    * getCurrentUser returns null if no user ID was saved as currently logged. It
     * returns the user ID otherwise.
    * */
    fun getCurrentUser(): Int? {
        val userId = ownSharedPreferences.getInt(CURRENT_USER_ID, -1)
        if (userId < 0) {
            return null
        }
        return userId
    }

    /**
     * signOut() removes the current user saved under shared preferences
     */
    fun signOut() {
        ownSharedPreferences.edit().putInt(CURRENT_USER_ID, -1).apply()
    }

    /**
     * isSignedIn() returns true if there is a valid userId saved as
     * logged in (userId > -1). Returns false otherwise
     */
    fun isSignedIn(): Boolean {
        val userId = ownSharedPreferences.getInt(CURRENT_USER_ID, -1)
        return userId > -1
    }
}