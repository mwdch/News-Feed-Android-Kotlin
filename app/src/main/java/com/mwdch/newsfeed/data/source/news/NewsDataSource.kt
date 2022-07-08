package com.mwdch.newsfeed.data.source.news

import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsDataSource {

    fun getNews(page: Int): Flow<NewsResponse>

    fun getFavoriteNews(): Flow<List<News>>

    suspend fun addToFavorites(news: News)

    suspend fun deleteFromFavorites(news: News)
}