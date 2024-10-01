package com.development.cancerdetection.data.repository

import com.development.cancerdetection.BuildConfig
import com.development.cancerdetection.data.remote.RemoteDataSource
import com.development.cancerdetection.data.remote.response.ArticlesItem
import com.development.cancerdetection.domain.repository.ArticleRepository
import com.development.cancerdetection.helper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ArticleRepository {
    override fun getArticles(): Flow<Resource<List<ArticlesItem>>> = flow {
        emit(Resource.Loading())
        try {
            val response = remoteDataSource.getArticles(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                val result = response.body()
                if (result != null) {
                    emit(Resource.Success(result.articles))
                }
            } else {
                val responseCode = response.code()
                emit(Resource.Error(response.message(), responseCode))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.message, errorCode = 404))
        } catch (e: HttpException) {
            emit(Resource.Error("Server bermasalah", e.code()))
        }
    }.flowOn(Dispatchers.IO)
}