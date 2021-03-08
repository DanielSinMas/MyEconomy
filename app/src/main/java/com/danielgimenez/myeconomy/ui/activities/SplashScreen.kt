package com.danielgimenez.myeconomy.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danielgimenez.myeconomy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth = Firebase.auth
        checkUserLogged()
    }

    private fun checkUserLogged(){
        val user = auth.currentUser
        startActivity(Intent(this, LoginActivity::class.java))
        /*if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
        }*/
    }
}