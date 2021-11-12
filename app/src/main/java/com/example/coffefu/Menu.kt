package com.example.coffefu

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

            // test
            val coffee1 = ProductPosition()
            coffee1.setId(1)
            coffee1.setName("Раф")
            coffee1.setPrice(180)

            val coffee2 = ProductPosition()
            coffee2.setId(2)
            coffee2.setName("Латте")
            coffee2.setPrice(120)

            val coffee = arrayOf(coffee1, coffee2)

            productsList = ArrayList()
            (productsList as MutableList<ProductPosition>).addAll(coffee)
            val coffeePositionsAdapter = CoffeePositionsAdapter(productsList, mainContext, mainActivity)
            coffeePositions.layoutManager = LinearLayoutManager(context)
            coffeePositions.adapter = coffeePositionsAdapter

        }
    }
}