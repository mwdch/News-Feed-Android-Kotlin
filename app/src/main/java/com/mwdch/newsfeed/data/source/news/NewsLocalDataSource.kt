package com.mwdch.newsfeed.data.source.news

import androidx.room.*
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsLocalDataSource : NewsDataSource {

    override fun getNews(page: Int): Flow<NewsResponse> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM tbl_news")
    override fun getFavoriteNews(): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addToFavorites(news: News)

    @Delete
    override suspend fun deleteFromFavorites(news: News)
}