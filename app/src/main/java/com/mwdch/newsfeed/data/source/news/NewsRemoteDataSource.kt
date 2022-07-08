package com.mwdch.newsfeed.data.source.news

import com.mwdch.newsfeed.common.REQUEST_KEY
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import com.mwdch.newsfeed.services.http.ApiService
import kotlinx.coroutines.flow.Flow

class NewsRemoteDataSource(private val apiService: ApiService) : NewsDataSource {

    override fun getNews(page: Int): Flow<NewsResponse> =
        apiService.getAllNews(REQUEST_KEY, page)

    override fun getFavoriteNews(): Flow<List<News>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(news: News) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(news: News) {
        TODO("Not yet implemented")
    }

}