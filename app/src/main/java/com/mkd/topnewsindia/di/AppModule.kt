package com.mkd.topnewsindia.di

import com.mkd.topnewsindia.usecase.GetNews
import com.mkd.topnewsindia.data.remote.NewsApi
import com.mkd.topnewsindia.domain.repository.NewsRepository
import com.mkd.topnewsindia.data.remote.NewsRepositoryImpl
import com.mkd.topnewsindia.usecase.NewsUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide a singleton instance of NewsApi using Retrofit
    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    // Provide a singleton instance of NewsRepository
    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }

    // Provide a singleton instance of NewsUseCases
    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository)
        )
    }
}