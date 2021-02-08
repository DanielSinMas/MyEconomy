package com.danielgimenez.myeconomy.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.utils.saveUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val GOOGLE_SIGN_IN = 100
    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private var db = Firebase.firestore
    private val USERS_COLLECTION = "users"

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton = findViewById<SignInButton>(R.id.signinbutton)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(this){
                            if(it.isSuccessful){
                                val user = auth.currentUser
                                insertUser(user)
                            }
                            else{
                                Toast.makeText(this, "Error:"+it.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
            } catch (e: ApiException) {
                Log.w("Error", "signInResult:failed code=" + e.statusCode)
            }
        }
    }

    private fun insertUser(user: FirebaseUser?){
        val userMap = hashMapOf(
            "email" to user?.email
        )
        db.collection(USERS_COLLECTION)
            .document(user?.email!!)
            .set(userMap)
            .addOnSuccessListener {
                saveUser(user)
                startActivity(Intent(this, MainActivity::class.java))
            }
    }
}