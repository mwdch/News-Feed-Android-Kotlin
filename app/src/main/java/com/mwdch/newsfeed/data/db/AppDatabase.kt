package com.mwdch.newsfeed.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.source.news.NewsLocalDataSource

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsLocalDataSource
}