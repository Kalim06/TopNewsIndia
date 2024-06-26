package com.mkd.topnewsindia.usecase

import androidx.paging.PagingData
import com.mkd.topnewsindia.data.model.Article
import com.mkd.topnewsindia.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    // Invoke function to get the news flow
    operator fun invoke(): Flow<PagingData<Article>> {
        return newsRepository.getNews()
    }
}