package com.mkd.topnewsindia.data.remote

import com.mkd.topnewsindia.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    // API call to get news headlines
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "in",
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = "6e11aa60dac04b4a9cc5f042f429d5f9"
    ): NewsResponse
}