package com.mwdch.newsfeed.feature.favorite

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

class FavoriteViewModel(private val newsRepository: NewsRepository) :
    ViewModel() {

    val messageLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()
    val favoriteNewsLiveData = MutableLiveData<List<News>>()

    fun deleteFromFavorites(news: News) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("FavoriteViewModel", throwable.message ?: "")
            messageLiveData.value = "news was not removed."
        }

        viewModelScope.launch(exceptionHandler) {
            newsRepository.deleteFromFavorites(news)
            news.isFavorite = false
            messageLiveData.value = "news was successfully removed."
        }
    }

    fun getFavorite() {
        progressBarLiveData.value = true
        viewModelScope.launch {
            newsRepository.getFavoriteNews()
                .catch {
                    messageLiveData.value = "Cannot get Favorites"
                }
                .onCompletion {
                    progressBarLiveData.value = false
                }
                .collect {
                    favoriteNewsLiveData.value = it
                }
        }
    }
}