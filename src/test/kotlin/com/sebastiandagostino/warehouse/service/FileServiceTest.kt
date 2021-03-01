package com.sebastiandagostino.warehouse.service

import com.sebastiandagostino.warehouse.exception.InputException
import org.junit.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions

class FileServiceTest {

    private val path = "src/test/resources/"
    private val inventoryFile = path + "inventory.json"
    private val productsFile = path + "products.json"

    @Test
    fun testLoadInventory() {
        val articles = FileService.loadArticles(inventoryFile)
        assertEquals(4, articles.size)
    }

    @Test
    fun testLoadProductCatalog() {
        val products = FileService.loadProducts(productsFile)
        assertEquals(2, products.size)
        for (product in products) {
            assertEquals(3, product.articles.size)
        }
    }

    @Test
    fun testLoadFails() {
        Assertions.assertThrows(
            InputException::class.java
        ) { FileService.loadArticles(path) }
    }
}