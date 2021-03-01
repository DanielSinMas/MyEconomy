package com.danielgimenez.myeconomy.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.login.LoginActivityModule
import com.danielgimenez.myeconomy.ui.App
import com.danielgimenez.myeconomy.ui.viewmodel.*
import com.danielgimenez.myeconomy.ui.viewmodel.states.ErrorLoginState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoadingLogintState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoginState
import com.danielgimenez.myeconomy.ui.viewmodel.states.SuccessLogintState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
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
        loginViewModel.loginLiveData.observe(this, loginObserver)
    }

    private var loginObserver = Observer<LoginState>{ state ->
        state.let {
            when(state){
                is SuccessLogintState -> {
                    var data = it.response as Response.Success
                    Log.e("Expenses", ""+data.data.expenses.size)
                }
                is LoadingLogintState -> {

                }
                is ErrorLoginState -> {

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
                doLogin(account!!)
            } catch (e: ApiException) {
                Log.w("Error", "signInResult:failed code=" + e.statusCode)
            }
        }
    }

    private fun doLogin(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    user?.getIdToken(true)!!.addOnCompleteListener{
                        if(it.isSuccessful){
                            var token = it.result!!.token
                            getDataForUser(token!!)
                        }
                    }
                } else {
                    Snackbar.make(signinbutton, "Error toh gordo kio", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getColor(R.color.error_color))
                            .setTextColor(getColor(R.color.white))
                            .show()
                }
            }
    }

    private fun getDataForUser(id_token: String){
        loginViewModel.getDataForUser(id_token)
    }

    private fun initiateMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}