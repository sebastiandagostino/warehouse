package com.sebastiandagostino.warehouse.service.dto

data class ProductDTO(
    val name: String,
    val contain_articles: List<ContainedArticleDTO>
)
