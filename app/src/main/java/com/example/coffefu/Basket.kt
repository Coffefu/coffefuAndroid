package com.example.coffefu

import android.os.Bundle
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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Basket : Fragment() {

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

        runBlocking {
            withContext(Dispatchers.IO) {
                productsList = DatabaseControl().getProductsTask(requireContext())
            }
        }

        // TODO override setProduct

//        val coffeePositionsAdapter: CoffeePositionsAdapter =
//            CoffeePositionsAdapter(productsList, context, mainActivity)
        coffeePositions.layoutManager = LinearLayoutManager(context)
//        coffeePositions.adapter = coffeePositionsAdapter
    }
}