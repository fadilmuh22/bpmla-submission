package com.dicoding.asclepius.ui.main

import com.dicoding.asclepius.ui.result.ResultViewModel


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.CancerClassificationsRepository
import com.dicoding.asclepius.di.Injection

class MainViewModelFactory private constructor(
    private val cancerClassificationsRepository: CancerClassificationsRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return MainViewModelFactory(cancerClassificationsRepository) as T
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
                )
            }.also { instance = it }
    }
}