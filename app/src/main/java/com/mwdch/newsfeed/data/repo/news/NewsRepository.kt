package com.mwdch.newsfeed.data.repo.news

import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(page: Int): Flow<List<News>>

    fun getFavoriteNews(): Flow<List<News>>

    suspend fun addToFavorites(news: News)

    suspend fun deleteFromFavorites(news: News)
}