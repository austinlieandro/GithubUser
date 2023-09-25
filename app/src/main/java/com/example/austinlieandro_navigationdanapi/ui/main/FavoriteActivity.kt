package com.example.austinlieandro_navigationdanapi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.austinlieandro_navigationdanapi.database.FavoriteUsers
import com.example.austinlieandro_navigationdanapi.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        favoriteViewModel.isLoading.observe(this){loading ->
            showLoading(loading)
        }

        favoriteViewModel.favoriteUsers.observe(this){favorite ->
            setFavoriteData(favorite)
        }

        favoriteViewModel.getFavoriteUsers()
    }

    private fun setFavoriteData(favorite: List<FavoriteUsers>) {
        val adapter = FavoriteAdapter()
        adapter.submitList(favorite)
        binding.rvFavorite.adapter = adapter
    }

    private fun showLoading(state: Boolean) { binding.progressBar6.visibility = if (state) View.VISIBLE else View.GONE }

    companion object{}
}