package com.example.coffefu.dao

import androidx.room.*
import com.example.coffefu.entities.ProductPosition


@Dao
interface OrderDao {
    @Query("SELECT * FROM `order` ORDER BY id DESC")
    fun getOrders(): List<ProductPosition>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: ProductPosition)

    @Query("DELETE FROM 'order' WHERE name = :name")
    fun deleteProduct(name: String)
}