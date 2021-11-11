package com.example.coffefu.database

import android.content.Context
import com.example.coffefu.entities.ProductPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseControl {
    suspend fun saveProductTask(applicationContext: Context, product: ProductPosition) {
        return withContext(Dispatchers.IO) {
            OrderDatabase.getAppDataBase(applicationContext)?.orderDao()?.saveProduct(product)
        }
    }

    suspend fun deleteProductTask(applicationContext: Context, name: String) {
        return withContext(Dispatchers.IO) {
            OrderDatabase.getAppDataBase(applicationContext)?.orderDao()?.deleteProduct(name)
        }
    }

    suspend fun getProductsTask(applicationContext: Context): List<ProductPosition> {
        val tmp: List<ProductPosition>
        withContext(Dispatchers.IO) {
            tmp = OrderDatabase.getAppDataBase(applicationContext)?.orderDao()?.getOrders()!!
        }
        return tmp
    }
}