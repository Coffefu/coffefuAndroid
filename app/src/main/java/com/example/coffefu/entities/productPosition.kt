package com.example.coffefu.entities

import java.io.Serializable
import androidx.room.*

interface PositionListener {
    fun onPositionClicked(product: ProductPosition, position: Int)
}

@Entity(tableName = "order")
class ProductPosition : Serializable {

    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    @ColumnInfo(name = "name")
    private var name: String = ""

    @ColumnInfo(name = "price")
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

    fun getPrice(): String {
        return "$price руб."
    }

    fun setPrice(price: Int) {
        this.price = price
    }

    override fun toString(): String {
        return name + price.toString()
    }
}