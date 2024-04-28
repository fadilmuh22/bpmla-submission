package com.dicoding.asclepius.ui.classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.NewsRepository
import com.dicoding.asclepius.di.Injection

class ClassificationViewModelFactory private constructor(
    private val newsRepository: NewsRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassificationViewModel::class.java)) {
            return ClassificationViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ClassificationViewModelFactory? = null

        fun getInstance(): ClassificationViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ClassificationViewModelFactory(
                    Injection.provideNewsRepository(),
                )
            }.also { instance = it }
    }
}