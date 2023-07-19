package com.iagocarvalho.booksstorefirebase.model.ModelApi

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)