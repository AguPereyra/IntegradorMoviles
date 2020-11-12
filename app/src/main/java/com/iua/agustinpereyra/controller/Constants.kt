package com.iua.agustinpereyra.controller

import com.iua.agustinpereyra.R

const val USERNAME = "USERNAME"
const val PASSWD = "PASSWD"
const val EMAIL = "EMAIL"
const val VIEW_USER_REQUEST = 1
const val STATE_USERNAME = "STATE_USERNAME"
const val STATE_PASSWORD = "STATE_PASSWORD"
const val STATE_PASSWORD_OLD = STATE_PASSWORD
const val STATE_PASSWORD_NEW = "STATE_PASSWORD_NEW"
const val STATE_PASSWORD_VALIDATE = "STATE_PASSWORD_VALIDATE"
const val STATE_EMAIL = "STATE_EMAIL"


/* Settings/Preferences */
//TODO:Check if can be improved/use some variable to make it be automatically modified if array changes
const val PREF_ORDER_BY_DEF = "sexo"
const val PREF_SEX_FILTER_DEF = "hembra"
const val PREF_DEF_SWITCH = false
const val PREF_DEF_WEIGHT = 1200
const val ORDER_BY_SEX = "sexo"
const val ORDER_BY_WEIGHT_DESC = "peso_des"
const val ORDER_BY_WEIGHT_ASC = "peso_asc"
const val KEY_ORDER_BY = "settings_order_by"
const val KEY_SEX_FILTER = "settings_filter_by_sex"
const val FEMALE = "hembra"
const val MALE = "macho"
const val MALE_FIRST_CAP = "Macho"
const val FEMALE_FIRST_CAP = "Hembra"
const val BOOLEAN_FEMALE = false
const val OWN_PREFS_NAME = "com.iua.agustinpereyra.prefs"

/* Register user */
const val REGISTRED_USER_EMAIL = "REGISTRED_USER_EMAIL"
const val REGISTRED_USER_PASSWD = "REGISTRED_USER_PASSWD"
const val REGISTRED_USER_NAME = "REGISTRED_USER_NAME"

/*API Logic*/
const val RANDOMUSER_API_MALE = "male"