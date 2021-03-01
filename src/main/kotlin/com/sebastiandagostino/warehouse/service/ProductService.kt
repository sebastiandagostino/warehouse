package com.sebastiandagostino.warehouse.service

import com.sebastiandagostino.warehouse.domain.Inventory
import com.sebastiandagostino.warehouse.domain.Product
import com.sebastiandagostino.warehouse.domain.ProductCatalog

object ProductService {

    private val productCatalog = ProductCatalog()

    fun getProducts(): List<Product> {
        return productCatalog.getProducts()
    }

    fun sell(product: Product, inventory: Inventory) {
        productCatalog.sellProduct(product, inventory)
    }
}