package com.dicoding.asclepius.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.CancerClassificationsRepository
import com.dicoding.asclepius.data.repository.NewsRepository
import com.dicoding.asclepius.di.Injection

class MainViewModelFactory private constructor(
    private val cancerClassificationsRepository: CancerClassificationsRepository,
    private val newsRepository: NewsRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(cancerClassificationsRepository, newsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

        companion object {
            @Volatile
            private var instance: MainViewModelFactory? = null

            fun getInstance(mApplication: Application): MainViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: MainViewModelFactory(
                        Injection.provideCancerClassificationRepository(mApplication),
                        Injection.provideNewsRepository(),
                    )
                }.also { instance = it }
        }
    }
