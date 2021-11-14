package com.example.coffefu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffefu.R
import com.example.coffefu.adapters.ProductsRecyclerAdapter
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.entities.ProductPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

interface ProductRecyclerListener {
    fun updateRecycleView()
}

class Basket : Fragment(), ProductRecyclerListener {
    private lateinit var productsRecyclerAdapter: ProductsRecyclerAdapter
    private lateinit var productsList: List<ProductPosition>
    private lateinit var coffeePositions: RecyclerView
    private lateinit var orderPrice: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        coffeePositions = view.findViewById(R.id.coffeePositions)
        orderPrice = view.findViewById(R.id.order_price)
        updateRecycleView()
    }

    override fun updateRecycleView() {
        runBlocking {
            withContext(Dispatchers.IO) {
                productsList = DatabaseControl().getSumCounts(requireContext())
            }
        }
        var sum = 0
        for (product in productsList) {
           sum += product.getPrice() * product.getCount()
        }
        orderPrice.text = "Итого: " + sum.toString() + " руб."
        productsRecyclerAdapter = ProductsRecyclerAdapter(productsList, context, "Basket", this)
        coffeePositions.layoutManager = LinearLayoutManager(context)
        coffeePositions.adapter = productsRecyclerAdapter
    }
}