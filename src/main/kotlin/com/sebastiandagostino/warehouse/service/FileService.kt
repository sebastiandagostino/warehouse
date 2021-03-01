package com.sebastiandagostino.warehouse.service

import com.google.gson.Gson
import com.sebastiandagostino.warehouse.domain.Article
import com.sebastiandagostino.warehouse.domain.Product
import com.sebastiandagostino.warehouse.service.dto.InventoryDTO
import com.sebastiandagostino.warehouse.service.dto.ProductCatalogDTO
import com.sebastiandagostino.warehouse.exception.InputException
import com.sebastiandagostino.warehouse.exception.MappingException
import org.slf4j.LoggerFactory
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.stream.Collectors

object FileService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val GSON = Gson()

    fun loadArticles(fileName: String): List<Article> {
        try {
            val file = readFile(fileName)
            val inventoryDTO = GSON.fromJson(file, InventoryDTO::class.java)
            return inventoryDTO.inventory.stream()
                .map {
                    if (it.stock.toLong() < 0)
                        throw MappingException()
                    Article(it.art_id.toLong(), it.name, AtomicLong(it.stock.toLong())) }
                .collect(Collectors.toList())
        } catch (e : MappingException) {
            throw e
        } catch (e: Exception) {
            logger.error("This could fail either IO, Json or mapping")
            throw InputException()
        }
    }

    fun loadProducts(fileName: String): List<Product> {
        try {
            val file = readFile(fileName)
            val catalogDTO = GSON.fromJson(file, ProductCatalogDTO::class.java)
            return catalogDTO.products.stream()
                .map {
                    val articles = it.contain_articles
                        .associate { a ->
                            if (a.amount_of.toInt() < 0)
                                throw MappingException()
                            Pair(a.art_id.toLong(), a.amount_of.toInt()) }
                        .toMutableMap()
                    Product(it.name, ConcurrentHashMap(articles))
                }
                .collect(Collectors.toList())
        } catch (e : MappingException) {
            throw e
        } catch (e: Exception) {
            logger.error("This could fail either IO, Json or mapping")
            throw InputException()
        }
    }

    private fun readFile(fileName: String): String =
        File(fileName).readText(Charsets.UTF_8)
}