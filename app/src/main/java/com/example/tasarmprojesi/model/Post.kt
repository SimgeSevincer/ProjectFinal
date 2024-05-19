package com.example.tasarmprojesi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var id: String = "",
    val userEmail: String,
    val comment: String,
    val downloadUrl: String,
    var likeCount: Long = 0,
    var commentCount: Long = 0
) : Parcelable {
    constructor() : this("", "", "", "") // Varsayılan (parametresiz) kurucu metodu tanımla
}
