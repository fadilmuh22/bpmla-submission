package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("q") query: String? = "cancer",
        @Query("category") category: String? = "health",
        @Query("language") country: String? = "en",
    ): ApiResponse
}
