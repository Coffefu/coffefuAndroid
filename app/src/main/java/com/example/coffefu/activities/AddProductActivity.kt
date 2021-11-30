package com.example.coffefu.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.coffefu.R
import com.example.coffefu.database.DatabaseControl
import com.example.coffefu.entities.ProductPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

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
        val sizeLayout = findViewById<ConstraintLayout>(R.id.sizeLayout)
        val productSize = findViewById<TextView>(R.id.product_size)

        val cupSizeM = findViewById<Button>(R.id.cup_size_m)
        val cupSizeS = findViewById<Button>(R.id.cup_size_s)
        var activeSize = cupSizeM

        fun setActive(active: Button) {
            cupSizeM.background = ContextCompat.getDrawable(this, R.drawable.rounded_border)
            cupSizeS.background = ContextCompat.getDrawable(this, R.drawable.rounded_border)
            active.background = ContextCompat.getDrawable(this, R.drawable.active_size)
            activeSize = active
        }

        cupSizeM.setOnClickListener { setActive(cupSizeM) }
        cupSizeS.setOnClickListener { setActive(cupSizeS) }

        var product: ProductPosition
        val productPrice = intent.getStringExtra("price")
        val productName = intent.getStringExtra("name")

        val prices = productPrice?.split("/")
        if (prices?.size != null && prices.size == 1) {
            sizeLayout.visibility = View.GONE
            productSize.visibility = View.GONE
        }

        // init layout
        productNameTextView.text = productName

        backButton.setOnClickListener {
            finish()
        }

        minusButton.setOnClickListener {
            if (productCountTextView.text.toString().toInt() > 1) {
                productCountTextView.text =
                    (productCountTextView.text.toString().toInt() - 1).toString()
            }
        }

        plusButton.setOnClickListener {
            productCountTextView.text =
                (productCountTextView.text.toString().toInt() + 1).toString()
        }

        finalAddButton.setOnClickListener {
            var activeSizeIndex = 0
            if (prices?.size != null && prices.size > 1) {
                activeSizeIndex = when(activeSize) {
                    cupSizeM -> 1
                    cupSizeS -> 0
                    else -> 0
                }
            }
            product = ProductPosition()
            product.setName(intent.getStringExtra("name")!!)
            product.setCount(productCountTextView.text.toString().toInt())
            product.setSize(activeSize.text as String)
            product.setPrice(prices!![activeSizeIndex])

            runBlocking {
                withContext(Dispatchers.IO) {
                    DatabaseControl().saveProductTask(applicationContext, product)
                }
            }
            finish()
        }


    }
}