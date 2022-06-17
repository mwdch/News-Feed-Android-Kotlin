package com.mwdch.newsfeed.data.repo.news

import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import com.mwdch.newsfeed.data.source.news.NewsDataSource
import com.mwdch.newsfeed.data.source.news.NewsLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class NewsRepositoryImpl(
    private val remoteDataSource: NewsDataSource,
    private val localDataSource: NewsLocalDataSource
) : NewsRepository {

    override fun getNews(page: Int): Single<NewsResponse> =
        localDataSource.getFavoriteNews()
            .flatMap { favoriteNews ->
                remoteDataSource.getNews(page).doOnSuccess {
                    //finding those news which is favorite from database
                    val favoriteNewsIds = favoriteNews.map { news ->
                        news.id
                    }
                    //changing the isFavorite value of each news for showing filled favorite icon
                    it.response.news.forEach { news ->
                        if (favoriteNewsIds.contains(news.id))
                            news.isFavorite = true
                    }
                }
            }

    override fun getFavoriteNews(): Single<List<News>> =
        localDataSource.getFavoriteNews()

    override fun addToFavorites(news: News): Completable =
        localDataSource.addToFavorites(news)

    override fun deleteFromFavorites(news: News): Completable =
        localDataSource.deleteFromFavorites(news)
}