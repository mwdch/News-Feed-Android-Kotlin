package com.mwdch.newsfeed.feature.detail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwdch.newsfeed.common.EXTRA_KEY_DATA
import com.mwdch.newsfeed.data.News

class NewsDetailViewModel(bundle: Bundle) : ViewModel() {

    val newsLiveData = MutableLiveData<News>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    init {
        progressBarLiveData.value = true
        newsLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }

}