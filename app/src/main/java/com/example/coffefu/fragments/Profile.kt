package com.example.coffefu.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.coffefu.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Profile(private var mainActivity: Activity) : Fragment(), ProductRecyclerListener {
    private lateinit var menuAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2
    private val tabNames = arrayOf("Мои данные", "История заказов")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        menuAdapter = CollectionAdapter(this, context, mainActivity, this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = menuAdapter

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    override fun updateRecycleView() {
        return
    }

}