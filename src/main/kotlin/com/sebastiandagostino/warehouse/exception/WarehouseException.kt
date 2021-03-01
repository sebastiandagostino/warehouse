package com.sebastiandagostino.warehouse.exception

open class WarehouseException : RuntimeException()

class InputException : WarehouseException()

class MappingException : WarehouseException()

class ProductDoesNotExistException : WarehouseException()

class ArticleNotFoundException : WarehouseException()

class OutOfStockException : WarehouseException()
