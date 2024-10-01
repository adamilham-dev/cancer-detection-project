package com.development.cancerdetection.di

import com.development.cancerdetection.data.repository.ArticleRepositoryImpl
import com.development.cancerdetection.data.repository.ResultRepositoryImpl
import com.development.cancerdetection.domain.repository.ArticleRepository
import com.development.cancerdetection.domain.repository.ResultRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideResultRepository(resultRepositoryImpl: ResultRepositoryImpl): ResultRepository

    @Binds
    abstract fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}