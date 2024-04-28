package com.dicoding.asclepius.ui.main

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.repository.CancerClassificationsRepository
import com.dicoding.asclepius.data.repository.NewsRepository

class MainViewModel(
    private val cancerClassificationsRepository: CancerClassificationsRepository,
    private val newsRepository: NewsRepository,
) :
    ViewModel() {
    fun getAllCancerClassifications() = cancerClassificationsRepository.getAllCancerClassifications()

    fun fetchTopHeadlines() = newsRepository.fetchTopHeadlines()
}
