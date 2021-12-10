package com.example.coffefu.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coffefu.fragments.Basket
import com.example.coffefu.fragments.Menu
import com.example.coffefu.R
import com.example.coffefu.fragments.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val menu = Menu(this)
    private val basket = Basket()
    private val profile = Profile()

    private var active: Fragment = menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_bar)

        supportFragmentManager.beginTransaction().add(R.id.flFragment, menu, "menu").commit()
        supportFragmentManager.beginTransaction().add(R.id.flFragment, basket, "basket")
            .hide(basket).commit()
        supportFragmentManager.beginTransaction().add(R.id.flFragment, profile).hide(profile)
            .commit()

        fun updateFragment(new: Fragment) {
            supportFragmentManager.beginTransaction().hide(active).show(new)
                .commit()
            active = new
        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> {
                    updateFragment(menu)
                }

                R.id.basket -> {
                    updateFragment(basket)
                    basket.updateRecycleView()
                }

                R.id.profile -> {
                    updateFragment(profile)
                }
            }
            true
        }

    }
}
