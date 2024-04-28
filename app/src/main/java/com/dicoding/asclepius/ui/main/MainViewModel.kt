package com.dicoding.asclepius.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.CancerClassificationsEntity
import com.dicoding.asclepius.data.repository.CancerClassificationsRepository
import kotlinx.coroutines.launch

class MainViewModel(private val cancerClassificationsRepository: CancerClassificationsRepository) :
    ViewModel() {
    fun getAllCancerClassifications() = cancerClassificationsRepository.getAllCancerClassifications()
}