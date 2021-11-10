package com.example.coffefu.dao

import androidx.room.*
import com.example.coffefu.entities.ProductPosition


@Dao
interface OrderDao {
    @Query("SELECT * FROM `order` ORDER BY id DESC")
    fun getOrder(): List<ProductPosition>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: ProductPosition)

    @Delete
    fun deleteProduct(product: ProductPosition)
}