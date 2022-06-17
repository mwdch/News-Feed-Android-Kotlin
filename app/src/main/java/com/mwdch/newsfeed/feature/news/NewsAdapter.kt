package com.mwdch.newsfeed.feature.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mwdch.newsfeed.R
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.databinding.ItemNewsBinding

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.Holder?>() {

    private lateinit var listener: OnNewsListener

    var newsList = ArrayList<News>()

    fun addNewsToExistingList(newsList: List<News>) {
        val lastSize = itemCount
        this.newsList.addAll(newsList)
        notifyItemRangeInserted(lastSize, itemCount)
    }

    fun setNewsList(newsList: List<News>) {
        this.newsList = newsList as ArrayList<News>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindNews(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setListener(listener: OnNewsListener) {
        this.listener = listener
    }

    inner class Holder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindNews(news: News) {
            binding.tvCategory.text = "category: ${news.category}"
            binding.tvTitle.text = news.title

            if (news.isFavorite)
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            else
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)

            itemView.setOnClickListener {
                listener.onNewsClick(news)
            }

            binding.ivFavorite.setOnClickListener {
                listener.onFavoriteClick(news)
                news.isFavorite = !news.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }
    }

    interface OnNewsListener {
        fun onNewsClick(news: News)

        fun onFavoriteClick(news: News)
    }
}