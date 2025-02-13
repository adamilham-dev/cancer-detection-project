package com.development.cancerdetection.data.remote

import com.development.cancerdetection.data.remote.retrofit.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getArticles(apiKey: String) = apiService.getArticles(apiKey)
}