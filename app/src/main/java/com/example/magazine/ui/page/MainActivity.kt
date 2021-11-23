package com.example.magazine.ui.page

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.magazine.*
import com.example.magazine.data.present.basket.OrderModel
import com.example.magazine.data.present.favorite.FavoriteModel
import com.example.magazine.interfaces.BaseView
import com.example.magazine.ui.page.auth.LoginActivity
import com.example.magazine.ui.page.auth.RegisterActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), BaseView {
    companion object{
        private lateinit var bottomNavigationView: BottomNavigationView
        private lateinit var basketBadge: BadgeDrawable
        private lateinit var favoriteBadge: BadgeDrawable

        fun changeBandageInBottomMenu(){
            if (OrderModel.orders.count() == 0){
                basketBadge.isVisible = false
            } else {
                basketBadge.isVisible = true
                basketBadge.number = OrderModel.orders.count()
            }

            if (FavoriteModel.favoritesList.count() == 0){
                favoriteBadge.isVisible = false
            } else {
                favoriteBadge.isVisible = true
                favoriteBadge.number = FavoriteModel.favoritesList.count()
            }
        }
    }

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val user = firebaseAuth.currentUser

        if(user != null){
            menuInflater.inflate(R.menu.menu_account_active, menu)
            menu!!.findItem(R.id.email).title = user.email.toString()
        }else{
            menuInflater.inflate(R.menu.menu_no_sign_in_account, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val user = firebaseAuth.currentUser

        if (user != null) {
            when (item.itemId) {
                R.id.email -> {

                }
                R.id.sign_out -> {
                    val intent = Intent(this, MainActivity::class.java)
                    Firebase.auth.signOut()
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            when (item.itemId) {
                R.id.sign_in -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }

                R.id.create_account -> {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return true
    }

    override fun bindViews() {
        firebaseAuth = Firebase.auth

        createToolbar()

        createBottomNavigationMenu()
    }

    override fun getContext(): Context {
        return this
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun createToolbar(){
        val topToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_toolbar)
        topToolbar.overflowIcon = this.getDrawable(R.drawable.ic_baseline_account_circle_32)
        setSupportActionBar(topToolbar)
    }

    private fun createBottomNavigationMenu(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_bottom_host_fragment) as NavHostFragment
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
        setBandageInBottomMenu()
        changeBandageInBottomMenu()
    }

    private fun setBandageInBottomMenu(){
        basketBadge = bottomNavigationView.getOrCreateBadge(R.id.nav_bottom_basket)
        basketBadge.backgroundColor = this.resources.getColor(R.color.pink_color_primary)
        favoriteBadge = bottomNavigationView.getOrCreateBadge(R.id.nav_bottom_favorites)
        favoriteBadge.backgroundColor = this.resources.getColor(R.color.pink_color_primary)
    }
}