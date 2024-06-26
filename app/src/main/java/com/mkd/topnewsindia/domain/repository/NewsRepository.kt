package com.mkd.topnewsindia.domain.repository

import androidx.paging.PagingData
import com.mkd.topnewsindia.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<PagingData<Article>>
}