package com.danielgimenez.myeconomy.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.data.source.database.MyEconomyDatabase
import com.danielgimenez.myeconomy.ui.activities.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.coroutineScope

fun Activity.saveUser(user: FirebaseUser){
    val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    with(sharedPref.edit()){
        putString("user", user.email)
        commit()
    }
}

fun Activity.getUser(): String? {
    val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    return sharedPref.getString("user", null)
}

fun Activity.performLogout(){
    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    mGoogleSignInClient.signOut()
    Firebase.auth.signOut()
    startActivity(Intent(this, LoginActivity::class.java))
}