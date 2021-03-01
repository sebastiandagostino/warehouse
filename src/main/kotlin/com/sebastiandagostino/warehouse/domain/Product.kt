package com.sebastiandagostino.warehouse.domain

import java.util.concurrent.ConcurrentHashMap

data class Product(
    val name: String,
    val articles: ConcurrentHashMap<Long, Int> // article id - amount
)