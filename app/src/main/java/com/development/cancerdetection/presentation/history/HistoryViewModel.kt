package com.development.cancerdetection.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.development.cancerdetection.domain.model.Result
import com.development.cancerdetection.domain.repository.ResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val resultRepository: ResultRepository
) : ViewModel() {

    fun getHistories() = resultRepository.getAllResults().asLiveData()

    fun deleteHistory(result: Result) = viewModelScope.launch {
        resultRepository.deleteResult(result)
    }
}