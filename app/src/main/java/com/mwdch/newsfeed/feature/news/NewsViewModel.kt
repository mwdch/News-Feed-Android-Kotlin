package com.mwdch.newsfeed.feature.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.data.repo.news.NewsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val messageLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    var page = 0

    val newsLiveData = MutableLiveData<List<News>>()

    fun addToFavorites(news: News) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("FavoriteViewModel", throwable.message ?: "")
        }

        viewModelScope.launch(exceptionHandler) {
            if (news.isFavorite) {
                newsRepository.deleteFromFavorites(news)
                news.isFavorite = false
            } else {
                newsRepository.addToFavorites(news)
                news.isFavorite = true
            }
        }
    }

    fun getNews() {
        progressBarLiveData.value = true
        viewModelScope.launch {
            newsRepository.getNews(++page)
                .catch {
                    messageLiveData.value = "Connection error."
                }
                .onCompletion {
                    progressBarLiveData.value = false
                }
                .collect {
                    newsLiveData.value = it
                }
        }
    }

}