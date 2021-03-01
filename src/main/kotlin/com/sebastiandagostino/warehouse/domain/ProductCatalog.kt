package com.sebastiandagostino.warehouse.domain

import com.sebastiandagostino.warehouse.exception.ProductDoesNotExistException
import java.util.concurrent.ConcurrentHashMap

class ProductCatalog {

    private val products = ConcurrentHashMap<String, Product>()

    fun addProduct(product: Product) {
        products[product.name] = product
    }

    fun getProducts() = products.elements().toList()

    fun sellProduct(product: Product, inventory: Inventory) {
        if (!products.containsKey(product.name)) {
            throw ProductDoesNotExistException()
        }
        inventory.removeProduct(product)
    }
}