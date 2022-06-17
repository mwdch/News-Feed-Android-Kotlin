package com.mwdch.newsfeed.feature.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mwdch.newsfeed.common.EXTRA_KEY_DATA
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.databinding.FragmentNewsBinding
import com.mwdch.newsfeed.feature.detail.NewsDetailActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : Fragment(), NewsAdapter.OnNewsListener {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val compositeDisposable = CompositeDisposable()

    private val viewModel: NewsViewModel by viewModel()

    private var newsAdapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
                binding.rvNews.visibility = View.GONE
            } else {
                binding.progressbar.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE
            }
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter?.addNewsToExistingList(it)
        }

        binding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getNews()
                }
            }
        })

        newsAdapter = NewsAdapter()
        newsAdapter?.setListener(this)
        binding.rvNews.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNews.adapter = newsAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        compositeDisposable.clear()
    }

    override fun onNewsClick(news: News) {
        requireContext().startActivity(
            Intent(
                requireContext(),
                NewsDetailActivity::class.java
            ).apply {
                putExtra(EXTRA_KEY_DATA, news)
            })
    }

    override fun onFavoriteClick(news: News) {
        viewModel.addToFavorites(news)
    }

}