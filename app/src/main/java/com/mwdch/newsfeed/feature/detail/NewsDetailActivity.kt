package com.mwdch.newsfeed.feature.detail

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.mwdch.newsfeed.databinding.ActivityNewsDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    private val viewModel: NewsDetailViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        viewModel.progressBarLiveData.observe(this) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
                binding.webView.visibility = View.GONE
            } else {
                binding.progressbar.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE
            }
        }

        viewModel.newsLiveData.observe(this) {
            viewModel.progressBarLiveData.value = false
            binding.webView.loadUrl(it.webUrl)
        }

    }
}