package com.example.coffefu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.coffefu.R
import com.example.coffefu.entities.ProductPosition

class CoffeePositionsAdapter(private var products: List<ProductPosition>) :
    RecyclerView.Adapter<CoffeePositionsAdapter.NoteViewHolder>() {


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coffeeName: TextView? = itemView.findViewById(R.id.coffee_name)
        private val coffeePrice: TextView? = itemView.findViewById(R.id.coffee_price)
        private val coffeeAdd: ToggleButton? = itemView.findViewById(R.id.coffee_add)

        fun setProduct(product: ProductPosition) {
            coffeeName?.text = product.getName()
            coffeePrice?.text = product.getPrice()

            coffeeAdd?.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {

                    // Add to database


                } else {

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.coffee_positions_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.setProduct(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}