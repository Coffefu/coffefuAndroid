package com.example.coffefu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

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
        supportFragmentManager.beginTransaction().add(R.id.flFragment, basket, "basket").hide(basket).commit()
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
                    // refresh basket to load new products
                    active = supportFragmentManager.findFragmentByTag("basket")!!
                    supportFragmentManager.beginTransaction().detach(active).commit()
                    supportFragmentManager.beginTransaction().attach(active).commit()
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
