package com.danielgimenez.myeconomy.ui.activities

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.ui.viewmodel.MainActivityViewModel
import com.danielgimenez.myeconomy.utils.getUser
import com.danielgimenez.myeconomy.utils.performLogout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_drawer_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.FirstFragment, R.id.SecondFragment, R.id.SettingsFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.findViewById<LinearLayout>(R.id.logout_layout).setOnClickListener {
            performLogout()
            viewModel.dropDatabase()
        }

        supportActionBar?.setHomeAsUpIndicator(R.drawable.drawable_icon_home)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpUserName(navView)
    }

    private fun setUpUserName(navigationView: NavigationView){
        val header = navigationView.getHeaderView(0)
        val userNameTv = header.findViewById<TextView>(R.id.menu_user_email)
        userNameTv.text = getUser()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                performLogout()
                viewModel.dropDatabase()
                return true
            }
            android.R.id.home -> {
                drawer_layout.openDrawer(Gravity.LEFT)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun sendEvent(id: String, name: String){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }
}