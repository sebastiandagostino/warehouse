package com.sebastiandagostino.warehouse.domain

import com.sebastiandagostino.warehouse.exception.ArticleNotFoundException
import com.sebastiandagostino.warehouse.exception.OutOfStockException
import java.util.concurrent.ConcurrentHashMap

class Inventory {

    private val articles = ConcurrentHashMap<Long, Article>() // article id

    fun getArticles() = articles.elements().toList()

    fun addArticle(article: Article) {
        if (!articles.containsKey(article.id)) {
            articles[article.id] = article
        }
    }

    fun removeProduct(product: Product) {
        val processed = mutableListOf<Pair<Long, Int>>()
        for ((articleId, articleAmount) in product.articles) {
            checkExistence(articleId, processed)
            removeStock(articleId, articleAmount, processed)
        }
    }

    private fun checkExistence(articleId : Long, processed : MutableList<Pair<Long, Int>>) {
        if (!articles.containsKey(articleId)) {
            rollback(processed)
            throw ArticleNotFoundException()
        }
    }

    private fun removeStock(articleId : Long, amount : Int, processed : MutableList<Pair<Long, Int>>) {
        var removed = false
        articles[articleId]!!.stock.updateAndGet { value ->
            if (value - amount >= 0) {
                removed = true
                value - amount
            } else {
                value
            }
        }
        if (!removed) {
            rollback(processed)
            throw OutOfStockException()
        }
        processed.add(Pair(articleId, amount))
    }

    private fun rollback(processed: List<Pair<Long, Int>>) {
        for ((articleId, articleAmount) in processed) {
            articles[articleId]!!.stock.updateAndGet{ value -> value + articleAmount }
        }
    }
}