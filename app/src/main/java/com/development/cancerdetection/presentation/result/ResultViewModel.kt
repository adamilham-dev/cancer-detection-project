package com.development.cancerdetection.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.cancerdetection.domain.model.Result
import com.development.cancerdetection.domain.repository.ResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val resultRepository: ResultRepository
): ViewModel() {

    fun insertResult(result: Result) = viewModelScope.launch {
        resultRepository.insertResult(result)
    }
}