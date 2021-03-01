package com.sebastiandagostino.warehouse.domain

import org.junit.Test
import java.util.concurrent.atomic.AtomicLong
import kotlin.test.assertEquals

class InventoryTest {

    @Test
    fun testAddArticle() {
        val inventory = Inventory()
        val article = Article(1, "1", AtomicLong(1))

        inventory.addArticle(article)

        val articles = inventory.getArticles()
        assertEquals(1, articles.size)
        assertEquals(article.id, articles[0].id)
    }

}