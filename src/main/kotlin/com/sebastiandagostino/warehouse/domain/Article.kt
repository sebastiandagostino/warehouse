package com.sebastiandagostino.warehouse.domain

import java.util.concurrent.atomic.AtomicLong

data class Article(
    val id: Long,
    val name: String,
    val stock: AtomicLong
)
