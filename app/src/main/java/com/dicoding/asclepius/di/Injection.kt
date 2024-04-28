package com.dicoding.asclepius.di

import android.app.Application
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.repository.CancerClassificationsRepository
import com.dicoding.asclepius.data.repository.NewsRepository

object Injection {
    fun provideNewsRepository(): NewsRepository {
        val apiService = ApiConfig.getApiService()
        return NewsRepository.getInstance(apiService)
    }

    fun provideCancerClassificationRepository(application: Application): CancerClassificationsRepository {
        return CancerClassificationsRepository(application)
    }
}