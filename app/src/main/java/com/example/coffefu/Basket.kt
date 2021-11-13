package com.example.coffefu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffefu.adapters.CoffeePositionsAdapter
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.entities.ProductPosition
import com.google.android.material.badge.BadgeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Basket : Fragment() {

    private lateinit var coffeePositionsAdapter: CoffeePositionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val coffeePositions = view.findViewById<RecyclerView>(R.id.coffeePositions)
        val productsList: List<ProductPosition>
        val deleteButtons = view.findViewById<Button?>(R.id.delete_product)

        coffeePositions.setOnClickListener {
            Log.e("buttons", deleteButtons.toString())
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                productsList = DatabaseControl().getSumCounts(requireContext())
            }
        }
        coffeePositionsAdapter = CoffeePositionsAdapter(productsList, context, "Basket")
        coffeePositions.layoutManager = LinearLayoutManager(context)
        coffeePositions.adapter = coffeePositionsAdapter


    }
}