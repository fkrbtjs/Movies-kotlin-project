package com.kyuseon.movies

import java.util.Date

data class ResultGetSearchMovies(
    var total: Int = 0,
    var start: Int = 0,
    var display: Int = 0,
    var items: List<Items>)

data class Items(
    val image: String = "",
    val title: String = "",
    val pubDate: String = "",
    val userRating: Double = 0.0,
    val link: String = ""
)
