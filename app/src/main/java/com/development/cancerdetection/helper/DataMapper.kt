package com.development.cancerdetection.helper

import android.net.Uri
import com.development.cancerdetection.data.local.entity.ResultEntity
import com.development.cancerdetection.domain.model.Result

object DataMapper {
    fun mapResultEntityToResult(data: ResultEntity) =
        Result(
            data.id,
            Uri.parse(data.imageUri),
            data.label,
            data.score,
            data.createdAt
        )

    fun Result.toEntity() =
        ResultEntity(
            id,
            imageUri.toString(),
            label,
            score,
            DateHelper.getCurrentDate()
        )

//    fun mapArticleResponseItemToArticle(data: ArticlesItem?) =
//        Article(
//            data?.title ?: "",
//            data?.urlToImage ?: "",
//            data?.url ?: "",
//            data?.author ?: ""
//        )
}