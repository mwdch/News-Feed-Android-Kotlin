package com.mwdch.newsfeed.feature.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mwdch.newsfeed.R
import com.mwdch.newsfeed.data.News
import com.mwdch.newsfeed.databinding.ItemNewsBinding

class FavoriteAdapter :
    RecyclerView.Adapter<FavoriteAdapter.Holder?>() {

    private lateinit var listener: OnFavoriteListener

    var newsList = ArrayList<News>()
        set(value) {
            field = value
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

    fun setListener(listener: OnFavoriteListener) {
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
                listener.onFavoriteDeleteClick(news)
                newsList.remove(news)
                notifyItemRemoved(adapterPosition)
                news.isFavorite = !news.isFavorite
            }
        }
    }

    interface OnFavoriteListener {
        fun onNewsClick(news: News)

        fun onFavoriteDeleteClick(news: News)
    }
}