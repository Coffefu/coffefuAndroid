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
import com.example.coffefu.activities.AddProductActivity
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.entities.ProductPosition
import com.example.coffefu.fragments.ProductRecyclerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class ProductsRecyclerAdapter(
    private var products: List<ProductPosition>,
    private var context: Context?,
    private var typeOfItems: String,
    private var productRecyclerListener: ProductRecyclerListener,
    private var mainActivity: Activity = Activity(),
    private var productSection: String = "coffee",
) :
    RecyclerView.Adapter<ProductsRecyclerAdapter.PositionViewHolder>() {

    class PositionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coffeeName: TextView? = itemView.findViewById(R.id.coffee_name)
        private val coffeePrice: TextView? = itemView.findViewById(R.id.coffee_price)
        private val coffeeAdd: Button? = itemView.findViewById(R.id.coffee_add)
        private val coffeeDelete: Button? = itemView.findViewById(R.id.delete_product)
        private val coffeeCount: TextView? = itemView.findViewById(R.id.coffee_count)


        fun setMenuProduct(product: ProductPosition, context: Context?, activity: Activity, productSection: String) {
            coffeeName?.text = product.getName()
            coffeePrice?.text = product.getStringPrice()

            coffeeAdd?.setOnClickListener {
                val intent = Intent(context, AddProductActivity::class.java)
                intent.putExtra("name", coffeeName?.text)
                intent.putExtra("price", product.getPrice())
                if (productSection == "Еда") {
                    intent.putExtra("type", "food")
                }
                activity.startActivity(intent)
            }
        }

        fun setBasketProduct(
            product: ProductPosition,
            context: Context?,
            productRecyclerListener: ProductRecyclerListener,
        ) {
            coffeeName?.text = product.getName()
            coffeePrice?.text = product.getStringPrice()
            coffeeCount?.text = product.getCount().toString() + " x"
            coffeeDelete?.setOnClickListener {
                runBlocking {
                    withContext(Dispatchers.IO) {
                        DatabaseControl().deleteProductTask(context!!, coffeeName?.text.toString())
                    }
                }
                productRecyclerListener.updateRecycleView()
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
            "Menu" -> holder.setMenuProduct(products[position], context, mainActivity, productSection)
            "Basket" -> holder.setBasketProduct(
                products[position],
                context,
                productRecyclerListener,
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