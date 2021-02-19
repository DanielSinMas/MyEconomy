package com.danielgimenez.myeconomy.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.login.LoginActivityModule
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.ui.App
import com.danielgimenez.myeconomy.ui.viewmodel.*
import com.danielgimenez.myeconomy.utils.saveUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val GOOGLE_SIGN_IN = 100
    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private var db = Firebase.firestore
    private val USERS_COLLECTION = "users"
    private val TYPES_COLLECTION = "types"
    private var isNewUser = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
        setUpInjection(App.graph)
        prepareViewModel()
        configView()
    }

    private fun configView(){
        auth = Firebase.auth
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

    private fun prepareViewModel(){
        loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        loginViewModel.insertTypeLiveData.observe(this, insertTypeOberserver)
    }

    private var insertTypeOberserver = Observer<InsertTypeState> { state ->
        state.let {
            when(state){
                is SuccessInsertTypetState -> {
                    insertTypesInFirestore((it.response as Response.Success).data)
                }
                is LoadingInsertTypetState -> {

                }
                is ErrorInsertTypeState -> {

                }
            }
        }
    }

    private fun setUpInjection(applicationComponent: ApplicationComponent){
        applicationComponent.plus(LoginActivityModule(this)).injectInto(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                checkIfUserExists(account!!)

            } catch (e: ApiException) {
                Log.w("Error", "signInResult:failed code=" + e.statusCode)
            }
        }
    }

    private fun checkIfUserExists(account: GoogleSignInAccount){
        val collection = db.collection(USERS_COLLECTION)
        collection.whereEqualTo("email", account.email).get().addOnCompleteListener{
            isNewUser = it.isSuccessful && it.result?.documents?.size!! == 0
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            val user = auth.currentUser
                            insertUser(user)
                        } else {
                            Snackbar.make(signinbutton, "Error toh gordo kio", Snackbar.LENGTH_SHORT)
                                    .setBackgroundTint(getColor(R.color.error_color))
                                    .setTextColor(getColor(R.color.white))
                                    .show()
                        }
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
                if(isNewUser){
                    //Preguntar por tipos y luego insertar
                    val type = Type(1, "Facturas")
                    val type2 = Type(2, "Comida")
                    val type3 = Type(3, "Entretenimiento")
                    loginViewModel.insertTypes(listOf(type, type2, type3))
                }
                else{
                    initiateMainActivity()
                }
            }
    }

    private fun insertTypesInFirestore(list: List<Type>){
        val user = auth.currentUser?.email!!
        db.runTransaction { transaction ->
            list.map { type ->
                transaction.set(db.collection(TYPES_COLLECTION).document(), type.toMap(user))
            }
        }.addOnSuccessListener {
            initiateMainActivity()
        }.addOnFailureListener{
            Toast.makeText(this, "Todo mal kio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initiateMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}