package com.example.coffefu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffefu.R
import java.io.Serializable

class ProductPosition : Serializable {
    private var id: Int = 0
    private var name: String = ""
    private var price: Int = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getPrice(): Int {
        return price
    }

    fun setPrice(price: Int) {
        this.price = price
    }
}

interface PositionListener {
    fun onPositionClicked(product: ProductPosition, position: Int)
}

class CoffeePositionsAdapter(private var products: List<ProductPosition>) :
    RecyclerView.Adapter<CoffeePositionsAdapter.NoteViewHolder>() {


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coffeName = itemView.findViewById<TextView>(R.id.coffee_name)
        val coffePrice = itemView.findViewById<TextView>(R.id.coffee_price)

        fun setProduct(product: ProductPosition) {
            coffeName?.text = product.getName()
            coffePrice?.text = product.getPrice().toString()
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
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}