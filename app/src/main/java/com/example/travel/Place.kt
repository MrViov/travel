package com.example.travel

data class Place(
    val id : Int,
    val title : String,
    val latitude : Double,
    val longitude : Double,
    val uris : ArrayList<String>,
    val describe : String
)
