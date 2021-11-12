package com.example.coffefu

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.coffefu.adapters.CoffeePositionsAdapter
import com.example.coffefu.entities.ProductPosition
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

val tabNames = arrayOf("Кофе", "Не кофе")

class Menu(private var mainActivity: Activity) : Fragment() {
    private lateinit var menuAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        menuAdapter = CollectionAdapter(this, context, mainActivity)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = menuAdapter

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }
}

class CollectionAdapter(fragment: Fragment, private var context: Context?, private var mainActivity: Activity) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = FragmentFactory(context, mainActivity)
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }
}

private const val ARG_OBJECT = "object"

class FragmentFactory(private var mainContext: Context?, private var mainActivity: Activity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {

            val coffeePositions = view.findViewById<RecyclerView>(R.id.coffeePositions)
            val productsList: List<ProductPosition>

            // TODO make data as json or maybe store in database!!!
            val coffeeNames = arrayOf("Раф", "Латте", "Американо", "Капучино", "Мокко")
            val coffeePrices = arrayOf(180, 120, 100, 150, 200)
            val coffee = mutableListOf<ProductPosition>()
            for (i in 0..4) {
                val tmpCoffee = ProductPosition()
                tmpCoffee.setId(i)
                tmpCoffee.setName(coffeeNames[i])
                tmpCoffee.setPrice(coffeePrices[i])
                coffee.add(tmpCoffee)
            }

            productsList = ArrayList()
            (productsList as MutableList<ProductPosition>).addAll(coffee)
            val coffeePositionsAdapter = CoffeePositionsAdapter(productsList, mainContext, "Menu", mainActivity)
            coffeePositions.layoutManager = LinearLayoutManager(context)
            coffeePositions.adapter = coffeePositionsAdapter

        }
    }
}