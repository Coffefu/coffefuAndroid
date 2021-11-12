package com.example.coffefu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val menu = Menu()
    private val basket = Basket()
    private val feedback = Feedback()

    private var active: Fragment = menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_bar)

        supportFragmentManager.beginTransaction().add(R.id.flFragment, menu).commit()
        supportFragmentManager.beginTransaction().add(R.id.flFragment, basket).hide(basket).commit()
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
