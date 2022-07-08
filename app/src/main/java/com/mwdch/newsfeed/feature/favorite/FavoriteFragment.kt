package com.mwdch.newsfeed.feature.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mwdch.newsfeed.common.EXTRA_KEY_DATA
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.databinding.FragmentFavoriteBinding
import com.mwdch.newsfeed.feature.detail.NewsDetailActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), FavoriteAdapter.OnFavoriteListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val compositeDisposable = CompositeDisposable()

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var favoriteAdapter: FavoriteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
                binding.parentLayout.visibility = View.GONE
            } else {
                binding.progressbar.visibility = View.GONE
                binding.parentLayout.visibility = View.VISIBLE
            }
        }

        favoriteViewModel.messageLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        favoriteViewModel.favoriteNewsLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.rvNews.visibility = View.GONE
            } else {
                favoriteAdapter?.newsList = it as ArrayList<News>
                binding.emptyState.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE
            }
        }

        //initializing adapter and recyclerview
        favoriteAdapter = FavoriteAdapter()
        favoriteAdapter?.setListener(this)
        binding.rvNews.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNews.adapter = favoriteAdapter
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser)
            favoriteViewModel.getFavorite()
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

    override fun onFavoriteDeleteClick(news: News) {
        favoriteViewModel.deleteFromFavorites(news)
        if (favoriteAdapter?.newsList!!.size == 1) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvNews.visibility = View.GONE
        }
    }

}