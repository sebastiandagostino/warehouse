package com.sebastiandagostino.warehouse.service

import com.sebastiandagostino.warehouse.domain.Article
import com.sebastiandagostino.warehouse.domain.Inventory

object InventoryService {

    val inventory = Inventory()

    fun getInventory() : List<Article> {
        return inventory.getArticles()
    }

    fun addInventory(articles: List<Article>) {
        articles.parallelStream().forEach { inventory.addArticle(it) }
    }
}