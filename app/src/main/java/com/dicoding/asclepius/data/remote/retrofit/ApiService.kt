package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.ApiResponse
import retrofit2.http.*
interface ApiService {
    @Multipart
    @GET("/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = "id",
        @Query("category") category: String? = "health"
    ): ApiResponse
}