package com.sebastiandagostino.warehouse.domain

import com.sebastiandagostino.warehouse.exception.ArticleNotFoundException
import com.sebastiandagostino.warehouse.exception.OutOfStockException
import com.sebastiandagostino.warehouse.exception.ProductDoesNotExistException
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import kotlin.test.assertEquals

class ProductCatalogTest {

    @Test
    fun testAddProduct() {
        val articles = ConcurrentHashMap<Long, Int>()
        articles[1] = 1
        val product = Product("prod", articles)
        val productCatalog = ProductCatalog()

        productCatalog.addProduct(product)

        val catalog = productCatalog.getProducts()
        assertEquals(1, catalog.size)
        assertEquals(product, catalog[0])
    }

    @Test
    fun testSellFailsOnProductThatDoesNotExist() {
        val inventory = Inventory()
        val articles = ConcurrentHashMap<Long, Int>()
        articles[1] = 1
        val product = Product("prod", articles)
        val productCatalog = ProductCatalog()

        assertThrows<ProductDoesNotExistException> {
            productCatalog.sellProduct(product, inventory)
        }
    }

    @Test
    fun testSellFailsOnArticleNotFound() {
        val inventory = Inventory()
        val articles = ConcurrentHashMap<Long, Int>()
        articles[1] = 1
        val product = Product("prod", articles)
        val productCatalog = ProductCatalog()
        productCatalog.addProduct(product)

        assertThrows<ArticleNotFoundException> {
            productCatalog.sellProduct(product, inventory)
        }
    }

    @Test
    fun testSellFailsOnInsufficientStock() {
        val inventory = Inventory()
        val articles = ConcurrentHashMap<Long, Int>()
        articles[1] = 2
        inventory.addArticle(Article(1, "article", AtomicLong(1)))
        val product = Product("prod", articles)
        val productCatalog = ProductCatalog()
        productCatalog.addProduct(product)

        assertThrows<OutOfStockException> {
            productCatalog.sellProduct(product, inventory)
        }

        assertEquals(1, inventory.getArticles()[0].stock.get())
    }

    @Test
    fun testSellProductSuccessfully() {
        val inventory = Inventory()
        val articles = ConcurrentHashMap<Long, Int>()
        articles[1] = 1
        inventory.addArticle(Article(1, "article", AtomicLong(1)))
        val product = Product("prod", articles)
        val productCatalog = ProductCatalog()
        productCatalog.addProduct(product)

        productCatalog.sellProduct(product, inventory)

        assertEquals(0, inventory.getArticles()[0].stock.get())
    }

}