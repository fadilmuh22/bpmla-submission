package com.dicoding.asclepius.ui.classification

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.repository.NewsRepository

class ClassificationViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel()