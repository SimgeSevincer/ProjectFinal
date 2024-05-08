package com.example.tasarmprojesi.model

data class Comment(
    var userEmail: String = "",
    var text: String = "",
    var timestamp: Long = 0
) {
    constructor() : this("", "", 0) // Parametresiz kurucu metod
}
