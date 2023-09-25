package com.example.austinlieandro_navigationdanapi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.austinlieandro_navigationdanapi.R
import com.example.austinlieandro_navigationdanapi.database.FavoriteUsers
import com.example.austinlieandro_navigationdanapi.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        detailViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }

        if (username != null) {
            detailViewModel.detailUser(username)

            detailViewModel.detail.observe(this) { detail ->
                binding.tvUsername.text = detail.login
                binding.tvName.text = detail.name
                Glide.with(this)
                    .load(detail.avatarUrl)
                    .into(binding.imgPersonDetail)
                binding.tvFollowers.text = "${detail.followers} Followers"
                binding.tvFollowing.text = "${detail.following} Following"


                val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
                val viewModel: FavoriteViewModel by viewModels {
                    factory
                }

                username?.let {
                    viewModel.checkIsFavorite(it)
                }

                val fabFavorite = binding.fabSaved

                viewModel.isFavorite.observe(this) { isFavorite ->
                    if (isFavorite) {
                        fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    } else {
                        fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }

                fabFavorite.setOnClickListener {
                    username?.let { username ->
                        val isCurrentlyFavorite = viewModel.isFavorite.value ?: false
                        if (isCurrentlyFavorite) {
                            val favoriteUsers = FavoriteUsers(username, detail.avatarUrl, false)
                            viewModel.removeFavoriteUser(favoriteUsers)
                        } else {
                            val favoriteUsers = FavoriteUsers(username, detail.avatarUrl, true)
                            viewModel.addFavoriteUser(favoriteUsers)
                        }
                    }
                }
            }
        }

        val sectionsPagerAdapter = username?.let { SectionsPagerAdapter(this, it) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(state: Boolean) { binding.progressBar2.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USERNAME = "extra_username"
    }
}