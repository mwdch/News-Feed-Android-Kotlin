package com.mwdch.newsfeed.common

import android.app.Application
import android.os.Bundle
import androidx.room.Room
import com.mwdch.newsfeed.data.db.AppDatabase
import com.mwdch.newsfeed.data.repo.news.NewsRepository
import com.mwdch.newsfeed.data.repo.news.NewsRepositoryImpl
import com.mwdch.newsfeed.data.source.news.NewsLocalDataSource
import com.mwdch.newsfeed.data.source.news.NewsRemoteDataSource
import com.mwdch.newsfeed.feature.detail.NewsDetailActivity
import com.mwdch.newsfeed.feature.detail.NewsDetailViewModel
import com.mwdch.newsfeed.feature.favorite.FavoriteViewModel
import com.mwdch.newsfeed.feature.news.NewsViewModel
import com.mwdch.newsfeed.services.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val myModules = module {
            single { createApiServiceInstance() }
            single { Room.databaseBuilder(this@App, AppDatabase::class.java, "db_app").build() }

            single<NewsRepository> {
                NewsRepositoryImpl(
                    NewsRemoteDataSource(get()),
                    get<AppDatabase>().newsDao()
                )
            }

            viewModel { FavoriteViewModel(get()) }
            viewModel { NewsViewModel(get()) }
            viewModel { (bundle: Bundle) -> NewsDetailViewModel(bundle) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }

}