package com.iua.agustinpereyra.controller

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.iua.agustinpereyra.R
import com.iua.agustinpereyra.model.User
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

    /**
    * Save login data of logged user Password should be encrypted!
    */
    public fun saveLoggedUser(email: String, passwd : String) {
        ownSharedPreferences.edit().putString(EMAIL, email).apply()
        // TODO: Only as poc, should be different
        ownSharedPreferences.edit().putString(USERNAME, ownSharedPreferences.getString(
            REGISTRED_USER_NAME, "")).apply()
    }

    /**
    * Returns null if no user was logged
    * */
    public fun getLoggedUser(): User? {
        val email = ownSharedPreferences.getString(EMAIL, "")
        val username = ownSharedPreferences.getString(USERNAME, "")
        if (email != null && username != null) {
            return User(email, username, "")
        } else {
            return null
        }
    }

    /**
     * Only creates one user, overrides if already exists
    */
    public fun saveRegisteredUser(email: String, username: String, passwd: String) {
        ownSharedPreferences.edit().putString(REGISTRED_USER_EMAIL, email).apply()
        ownSharedPreferences.edit().putString(REGISTRED_USER_NAME, username).apply()
        ownSharedPreferences.edit().putString(REGISTRED_USER_PASSWD, passwd).apply()
    }

    /**
     * Returned registered user
     */
    public fun getRegisteredUser(): User? {
        val email = ownSharedPreferences.getString(REGISTRED_USER_EMAIL, "")
        val username = ownSharedPreferences.getString(REGISTRED_USER_EMAIL, "")
        val passwd = ownSharedPreferences.getString(REGISTRED_USER_PASSWD, "")
        if (email != null && username != null && passwd != null) {
            return User(email, username, passwd)
        } else {
            return null
        }
    }

    public fun changeRegisteredUsername(username: String) {
        ownSharedPreferences.edit().putString(REGISTRED_USER_NAME, username).apply()
        ownSharedPreferences.edit().putString(USERNAME, username).apply()
    }

    public fun changeRegisteredPasswd(passwd: String) {
        ownSharedPreferences.edit().putString(REGISTRED_USER_PASSWD, passwd).apply()
    }
}