package com.mwdch.newsfeed.data.source.news

import androidx.room.*
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NewsLocalDataSource : NewsDataSource {
    override fun getNews(page: Int): Single<NewsResponse> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM tbl_news")
    override fun getFavoriteNews(): Single<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorites(news: News): Completable

    @Delete
    override fun deleteFromFavorites(news: News): Completable
}