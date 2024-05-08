package com.example.tasarmprojesi.model

data class Article(
    val makaleID: String,
    val makaleCategory: String,
    val makaleImage: String,
    val makaleName: String,
    val makaleDescription: String,
    val makaleDate: String,
    val makaleFavCount: Int
)
