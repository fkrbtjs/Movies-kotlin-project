package com.kyuseon.movies

import android.os.Parcel
import android.os.Parcelable


data class MoviesVO(
    val title: String? = "",
    val image: String? = "",
    val pubDate: String? = "",
    val userRating: Double = 0.0,
    val link: String? = ""
)