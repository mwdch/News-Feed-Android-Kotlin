package com.mwdch.newsfeed.feature.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.repo.news.NewsRepository
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel(private val newsRepository: NewsRepository) :
    ViewModel() {

    val favoriteNewsLiveData = MutableLiveData<List<News>>()
    val messageLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()

    fun getFavorites() {
        progressBarLiveData.value = true
        newsRepository.getFavoriteNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : SingleObserver<List<News>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<News>) {
                    favoriteNewsLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    messageLiveData.value = "Cannot get Favorites"
                }
            })
    }

    fun deleteFromFavorites(news: News) {
        newsRepository.deleteFromFavorites(news)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    news.isFavorite = false
                    messageLiveData.value = "news was successfully removed."
                }

                override fun onError(e: Throwable) {
                    messageLiveData.value = "news was not removed."
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}