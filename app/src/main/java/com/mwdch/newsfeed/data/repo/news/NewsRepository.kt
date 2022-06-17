package com.mwdch.newsfeed.data.repo.news

import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import io.reactivex.Completable
import io.reactivex.Single

interface NewsRepository {

    fun getNews(page: Int): Single<NewsResponse>

    fun getFavoriteNews(): Single<List<News>>

    fun addToFavorites(news: News): Completable

    fun deleteFromFavorites(news: News): Completable
}