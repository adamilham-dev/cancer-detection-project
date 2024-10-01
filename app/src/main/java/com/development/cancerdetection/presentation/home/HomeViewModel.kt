package com.development.cancerdetection.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.cancerdetection.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    fun getArticles() = articleRepository.getArticles().asLiveData()
}