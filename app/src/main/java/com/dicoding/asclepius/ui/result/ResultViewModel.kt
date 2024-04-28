package com.dicoding.asclepius.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.CancerClassificationsEntity
import com.dicoding.asclepius.data.repository.CancerClassificationsRepository
import kotlinx.coroutines.launch

class ResultViewModel(private val cancerClassificationsRepository: CancerClassificationsRepository) :
    ViewModel() {

    fun saveCancerClassification(cancerClassificationEntity: CancerClassificationsEntity) {
        viewModelScope.launch {
            cancerClassificationsRepository.insert(cancerClassificationEntity)
        }
    }

    fun deleteCancerClassification(cancerClassificationEntity: CancerClassificationsEntity) {
        viewModelScope.launch {
            cancerClassificationsRepository.delete(cancerClassificationEntity)
        }
    }
}