package com.danielgimenez.myeconomy.utils

import android.app.Activity
import android.content.Context
import com.danielgimenez.myeconomy.R
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

fun Activity.saveUser(user: FirebaseUser){
    val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    with(sharedPref.edit()){
        putString("user", user.email)
        commit()
    }
}

fun Activity.getUser(): String? {
    val sharedPref = getPreferences(Context.MODE_PRIVATE)
    return sharedPref.getString("user", null)
}