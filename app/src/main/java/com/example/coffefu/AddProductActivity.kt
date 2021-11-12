package com.example.coffefu

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.database.OrderDatabase
import com.example.coffefu.entities.ProductPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val backButton = findViewById<Button>(R.id.back_btn)
        val minusButton = findViewById<Button>(R.id.minus_count)
        val productCountTextView = findViewById<TextView>(R.id.product_count)
        val plusButton = findViewById<Button>(R.id.plus_count)
        val finalAddButton = findViewById<Button>(R.id.add_btn)
        val productNameTextView = findViewById<TextView>(R.id.product_name)
        val orderPriceTextView = findViewById<TextView>(R.id.order_price)

        var product = ProductPosition()
        val productPrice = intent.getIntExtra("price", 0)
        val productName = intent.getStringExtra("name")


        // init layout
        productNameTextView.text = productName
        orderPriceTextView.text = "Итого: $productPrice руб."

        backButton.setOnClickListener {
            finish()
        }

        fun updateOrderPrice() {
            val tmp = productPrice * productCountTextView.text.toString().toInt()
            orderPriceTextView.text = "Итого: $tmp руб."
        }

        minusButton.setOnClickListener {
            if (productCountTextView.text.toString().toInt() > 0) {
                productCountTextView.text =
                    (productCountTextView.text.toString().toInt() - 1).toString()
            }
            updateOrderPrice()
        }

        plusButton.setOnClickListener {
            productCountTextView.text =
                (productCountTextView.text.toString().toInt() + 1).toString()
            updateOrderPrice()
        }

        // TODO не давать заказать 0
        finalAddButton.setOnClickListener {
            product = ProductPosition()
            product.setName(intent.getStringExtra("name")!!)
            product.setCount(productCountTextView.text.toString().toInt())
            product.setPrice(productPrice.toString().toInt())

            runBlocking {
                withContext(Dispatchers.IO) {
                    DatabaseControl().saveProductTask(applicationContext, product)
                }
            }
            finish()
        }


    }
}