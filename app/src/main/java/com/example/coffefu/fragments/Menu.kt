package com.example.coffefu.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.coffefu.R
import com.example.coffefu.adapters.ProductsRecyclerAdapter
import com.example.coffefu.entities.ProductPosition
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

val tabNames = arrayOf("Кофе", "Не кофе")

class Menu(private var mainActivity: Activity) : Fragment(), ProductRecyclerListener {
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
        menuAdapter = CollectionAdapter(this, context, mainActivity, this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = menuAdapter

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    override fun updateRecycleView() {}
}

class CollectionAdapter(
    fragment: Fragment,
    private var context: Context?,
    private var mainActivity: Activity,
    private var productRecyclerListener: ProductRecyclerListener
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabNames.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        val fragment = FragmentFactory(context, mainActivity, productRecyclerListener, position)
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt(ARG_OBJECT, position + 1)
        }
        return fragment
    }
}

private const val ARG_OBJECT = "object"

class FragmentFactory(
    private var mainContext: Context?,
    private var mainActivity: Activity,
    private var productRecyclerListener: ProductRecyclerListener,
    private var position: Int
) : Fragment() {

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

            var productNames = arrayOf("")
            var productsPrices = arrayOf("")

            // TODO make data as json or maybe store in database!!!
            when (position) {
                0 -> {
                    productNames = arrayOf(
                        "Эспрессо двойной",
                        "Фильтр кофе",
                        "Американо",
                        "Американо с молоком",
                        "Американо на молоке",
                        "Капучино",
                        "Латте",
                        "Флэт Уайт",
                        "Раф классический",
                        "Мокко",
                        "Айс Латте",
                        "Медовый американо",
                        "Медовый капучино с османтусом",
                        "Тайский Латте",
                        "Раф пряный",
                        "Раф ОРЕО",
                        "Раф \"Банановый пирог\"",
                        "Бамбл жасмин-грейпфрут"
                    )
                    productsPrices = arrayOf(
                        "110",
                        "90/110",
                        "90/110",
                        "120/140",
                        "130/150",
                        "130/180",
                        "150/180",
                        "150",
                        "185/225",
                        "180",
                        "180",
                        "150",
                        "180",
                        "200",
                        "225",
                        "250",
                        "250",
                        "175"
                    )
                }
                1 -> {
                    productNames = arrayOf(
                        "Горячий шоколад",
                        "Какао",
                        "Какао с маршмелоу",
                        "Матча латте",
                        "Черный чай",
                        "Зеленый чай",
                        "Фруктовый чай",
                        "Облепиховый чай",
                        "Медовый час с жасмином и персиком",
                        "Ягодный ТиДжо с помелло",
                        "Лили Роуз",
                        "Манго-манго",
                        "Пинк-матча",
                        "Матча-латте",
                        "Лимонад апельсин-личи",
                        "Лимонад жасмин-грейпфрут",
                        "Лимонад классический",
                        "Лимонад мохитовый джем",
                        "Матча-лимонад",
                        "Фондо-марино",
                        "Фрэш Доуп Киви",
                        "Банана-матча",
                        "Кокосовый какао"
                    )
                    productsPrices = arrayOf(
                        "175",
                        "180",
                        "210",
                        "175",
                        "80",
                        "80",
                        "80",
                        "175",
                        "175",
                        "185",
                        "185",
                        "185",
                        "200",
                        "175/200",
                        "180",
                        "180",
                        "180",
                        "180",
                        "200",
                        "175",
                        "200",
                        "225",
                        "225"
                    )
                }
            }
            val coffee = mutableListOf<ProductPosition>()
            for (i in productNames.indices) {
                val tmpCoffee = ProductPosition()
                tmpCoffee.setId(i)
                tmpCoffee.setName(productNames[i])
                tmpCoffee.setPrice(productsPrices[i])
                coffee.add(tmpCoffee)
            }

            productsList = ArrayList()
            (productsList as MutableList<ProductPosition>).addAll(coffee)
            val coffeePositionsAdapter = ProductsRecyclerAdapter(
                productsList,
                mainContext,
                "Menu",
                productRecyclerListener,
                mainActivity,
                tabNames[position]
            )
            coffeePositions.layoutManager = LinearLayoutManager(context)
            coffeePositions.adapter = coffeePositionsAdapter

        }
    }
}