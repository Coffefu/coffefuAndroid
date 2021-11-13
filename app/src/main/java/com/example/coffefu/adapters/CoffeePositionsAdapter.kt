package com.example.coffefu.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffefu.*
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.entities.ProductPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class CoffeePositionsAdapter(
    private var products: List<ProductPosition>,
    private var context: Context?,
    private var typeOfItems: String,
    private var coffeePositionsListener: CoffeePositionsListener,
    private var mainActivity: Activity = Activity(),
) :
    RecyclerView.Adapter<CoffeePositionsAdapter.PositionViewHolder>() {

    class PositionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coffeeName: TextView? = itemView.findViewById(R.id.coffee_name)
        private val coffeePrice: TextView? = itemView.findViewById(R.id.coffee_price)
        private val coffeeAdd: Button? = itemView.findViewById(R.id.coffee_add)
        private val coffeeDelete: Button? = itemView.findViewById(R.id.delete_product)


        fun setMenuProduct(product: ProductPosition, context: Context?, activity: Activity) {
            coffeeName?.text = product.getName()
            coffeePrice?.text = product.getStringPrice()

            coffeeAdd?.setOnClickListener {
                // TODO активити с результатом, обработка разных кодов возврата
                val intent = Intent(context, AddProductActivity::class.java)
                intent.putExtra("name", coffeeName?.text)
                intent.putExtra("price", product.getPrice())
                activity.startActivityForResult(intent, 1)
            }
        }

        fun setBasketProduct(
            product: ProductPosition,
            context: Context?,
            activity: Activity,
            coffeePositionsListener: CoffeePositionsListener,
            position: Int
        ) {
            coffeeName?.text = product.getName()
            coffeePrice?.text = product.getStringPrice()
            coffeeDelete?.setOnClickListener {
                runBlocking {
                    withContext(Dispatchers.IO) {
                        DatabaseControl().deleteProductTask(context!!, coffeeName?.text.toString())
                    }
                }
                coffeePositionsListener.onClick(coffeeDelete, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        when (typeOfItems) {
            "Menu" -> {
                return PositionViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_positions_item, parent, false)
                )
            }
            "Basket" -> {
                return PositionViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.basket_positions_item, parent, false)
                )
            }
            else -> return PositionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.menu_positions_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        when (typeOfItems) {
            "Menu" -> holder.setMenuProduct(products[position], context, mainActivity)
            "Basket" -> holder.setBasketProduct(
                products[position],
                context,
                mainActivity,
                coffeePositionsListener,
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}