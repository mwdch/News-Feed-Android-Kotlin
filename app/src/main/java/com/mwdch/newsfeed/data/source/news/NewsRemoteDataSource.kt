package com.mwdch.newsfeed.data.source.news

import com.mwdch.newsfeed.common.REQUEST_KEY
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import com.mwdch.newsfeed.services.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class NewsRemoteDataSource(private val apiService: ApiService) : NewsDataSource {

    override fun getNews(page: Int): Single<NewsResponse> =
        apiService.getAllNews(REQUEST_KEY, page)

    override fun getFavoriteNews(): Single<List<News>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(news: News): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(news: News): Completable {
        TODO("Not yet implemented")
    }

}