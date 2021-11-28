package com.example.coffefu.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

interface PositionListener {
    fun onPositionClicked(product: ProductPosition, position: Int)
}

@Entity(tableName = "order")
class ProductPosition : Serializable {

    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    @ColumnInfo(name = "name")
    private var name: String = ""

    @ColumnInfo(name = "size")
    private var size: String = ""

    @ColumnInfo(name = "price")
    private var price: Int = 0

    @ColumnInfo(name = "count")
    private var count: Int = 0

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

    fun getSize(): String {
        return size
    }

    fun setSize(size: String) {
        this.size = size
    }

    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
    }

    fun getStringPrice(): String {
        return "$price руб."
    }

    fun getPrice(): Int {
        return price
    }

    fun setPrice(price: Int) {
        this.price = price
    }

    override fun toString(): String {
        return name + price.toString()
    }
}