package com.mwdch.newsfeed.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.mwdch.newsfeed.R
import com.mwdch.newsfeed.feature.news.NewsFragment
import com.mwdch.newsfeed.databinding.ActivityMainBinding
import com.mwdch.newsfeed.feature.favorite.FavoriteFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(NewsFragment(), getString(R.string.news))
        adapter.addFragment(FavoriteFragment(), getString(R.string.favorites))

        binding.viewPager.adapter = adapter

        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}