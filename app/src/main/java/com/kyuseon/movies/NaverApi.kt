package com.kyuseon.movies


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverApi {
    @GET("search/movie.json")
    fun getSearchMovies(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int? = 100,
        @Query("start") start: Int? = 1
    ): Call<ResultGetSearchMovies>
}