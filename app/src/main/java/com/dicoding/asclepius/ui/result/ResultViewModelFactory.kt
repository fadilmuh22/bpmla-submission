package com.dicoding.asclepius.ui.result

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.CancerClassificationsRepository
import com.dicoding.asclepius.di.Injection

class ResultViewModelFactory private constructor(
    private val cancerClassificationsRepository: CancerClassificationsRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
                return ResultViewModel(cancerClassificationsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

        companion object {
            @Volatile
            private var instance: ResultViewModelFactory? = null

            fun getInstance(mApplication: Application): ResultViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ResultViewModelFactory(
                        Injection.provideCancerClassificationRepository(mApplication),
                    )
                }.also { instance = it }
        }
    }
