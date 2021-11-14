package com.example.coffefu.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coffefu.fragments.Basket
import com.example.coffefu.fragments.Menu
import com.example.coffefu.R
import com.example.coffefu.fragments.Feedback
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val menu = Menu(this)
    private val basket = Basket()
    private val feedback = Feedback()

    private var active: Fragment = menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_bar)

        supportFragmentManager.beginTransaction().add(R.id.flFragment, menu, "menu").commit()
        supportFragmentManager.beginTransaction().add(R.id.flFragment, basket, "basket")
            .hide(basket).commit()
        supportFragmentManager.beginTransaction().add(R.id.flFragment, feedback).hide(feedback)
            .commit()

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> {
                    supportFragmentManager.beginTransaction().hide(active).show(menu)
                        .commit()
                    active = menu
                }

                R.id.basket -> {
                    supportFragmentManager.beginTransaction().hide(active).show(basket)
                        .commit()
                    active = basket
                    basket.updateRecycleView()
                }

                R.id.feedback -> {
                    supportFragmentManager.beginTransaction().hide(active).show(feedback)
                        .commit()
                    active = feedback
                }
            }
            true
        }

    }
}
