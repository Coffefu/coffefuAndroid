package com.example.coffefu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_bar)

        val firstFragment = Menu()
        val secondFragment = Basket()
        val thirdFragment = Feedback()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu->setCurrentFragment(firstFragment)
                R.id.basket->setCurrentFragment(secondFragment)
                R.id.feedback->setCurrentFragment(thirdFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace( R.id.flFragment, fragment)
            commit()
        }
}
