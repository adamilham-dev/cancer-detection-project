package com.development.cancerdetection.domain.repository

import com.development.cancerdetection.data.remote.response.ArticlesItem
import com.development.cancerdetection.helper.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getArticles(): Flow<Resource<List<ArticlesItem>>>
}