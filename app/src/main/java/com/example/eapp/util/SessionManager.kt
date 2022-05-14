package com.example.eapp.util

import android.content.Context

class SessionManager(context: Context) {

    var PRIVATE_MODE = 0
    val PREF_NAME = "EApp"
    val bid = ""
    val pri = ""
    val KEY_IS_LOGGEDIN = "isLoggedIn"
    val KEY_IS_FAV = "isFav"

    var pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor = pref.edit()

    fun setLogin(isLoggedIn: Boolean){
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn)
        editor.apply()
    }
    fun setFavFrag(isFav: Boolean){
        editor.putBoolean(KEY_IS_FAV, isFav)
        editor.apply()
    }
    fun putUid(uid : String){
        editor.putString(bid,uid)
        editor.apply()
    }
    fun getUid(): String? {
        return pref.getString(bid,null)
    }

    fun isFav(): Boolean {
        return pref.getBoolean(KEY_IS_FAV, false)
    }


    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false)
    }

}