package com.mwdch.newsfeed.data.repo.news

import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.source.news.NewsDataSource
import com.mwdch.newsfeed.data.source.news.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val remoteDataSource: NewsDataSource,
    private val localDataSource: NewsLocalDataSource
) : NewsRepository {

    override fun getNews(page: Int): Flow<List<News>> =
        remoteDataSource.getNews(page)
            .map { newsResponse -> newsResponse.response.news }


//    override fun getNews(page: Int): Single<NewsResponse> =
//        localDataSource.getFavoriteNews()
//            .flatMap { favoriteNews ->
//                remoteDataSource.getNews(page).doOnSuccess {
//                    //finding those news which is favorite from database
//                    val favoriteNewsIds = favoriteNews.map { news ->
//                        news.id
//                    }
//                    //changing the isFavorite value of each news for showing filled favorite icon
//                    it.response.news.forEach { news ->
//                        if (favoriteNewsIds.contains(news.id))
//                            news.isFavorite = true
//                    }
//                }
//            }

    override fun getFavoriteNews(): Flow<List<News>> =
        localDataSource.getFavoriteNews()

    override suspend fun addToFavorites(news: News) =
        localDataSource.addToFavorites(news)

    override suspend fun deleteFromFavorites(news: News) =
        localDataSource.deleteFromFavorites(news)
}