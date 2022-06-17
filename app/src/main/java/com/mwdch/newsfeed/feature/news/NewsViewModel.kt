package com.mwdch.newsfeed.feature.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.NewsResponse
import com.mwdch.newsfeed.data.repo.news.NewsRepository
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val newsLiveData = MutableLiveData<List<News>>()
    val messageLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()
    var page = 0

    fun getNews() {
        progressBarLiveData.value = true
        newsRepository.getNews(++page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : SingleObserver<NewsResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: NewsResponse) {
                    newsLiveData.value = t.response.news
                }

                override fun onError(e: Throwable) {
                    messageLiveData.value = "Connection error."
                }
            })
    }

    fun addToFavorites(news: News) {
        if (news.isFavorite)
            newsRepository.deleteFromFavorites(news)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        news.isFavorite = false
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        else
            newsRepository.addToFavorites(news)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        news.isFavorite = true
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}