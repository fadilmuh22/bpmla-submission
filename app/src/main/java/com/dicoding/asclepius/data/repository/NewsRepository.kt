package com.dicoding.asclepius.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.asclepius.data.remote.response.ApiResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiService
import com.dicoding.asclepius.data.Result

class NewsRepository private constructor(
    private val apiService: ApiService,
) {

    fun fetchTopHeadlines(): LiveData<Result<ApiResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getTopHeadlines()
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d(TAG, "fetchTopHeadlines: ${e.message}")
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        const val TAG = "NewsRepository"

        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(apiService: ApiService): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService)
            }.also { instance = it }
    }
}