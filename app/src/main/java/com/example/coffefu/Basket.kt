package com.example.coffefu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffefu.adapters.CoffeePositionsAdapter
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.entities.ProductPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.reflect.typeOf

interface CoffeePositionsListener {
    fun onClick(view: View?, position: Int)
}

class Basket : Fragment(), CoffeePositionsListener {
    private lateinit var coffeePositionsAdapter: CoffeePositionsAdapter
    private lateinit var productsList: List<ProductPosition>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val coffeePositions = view.findViewById<RecyclerView>(R.id.coffeePositions)

        runBlocking {
            withContext(Dispatchers.IO) {
                productsList = DatabaseControl().getSumCounts(requireContext())
            }
        }
        coffeePositionsAdapter = CoffeePositionsAdapter(productsList, context, "Basket", this)
        coffeePositions.layoutManager = LinearLayoutManager(context)
        coffeePositions.adapter = coffeePositionsAdapter

    }

    override fun onClick(view: View?, position: Int) {
         runBlocking {
            withContext(Dispatchers.IO) {
                productsList = DatabaseControl().getSumCounts(requireContext())
            }
        }
        coffeePositionsAdapter.notifyItemRemoved(position)
    }
}