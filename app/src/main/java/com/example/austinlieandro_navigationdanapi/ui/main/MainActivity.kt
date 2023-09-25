package com.example.austinlieandro_navigationdanapi.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.austinlieandro_navigationdanapi.R
import com.example.austinlieandro_navigationdanapi.data.response.ItemsItem
import com.example.austinlieandro_navigationdanapi.data.response.ResponseItem
import com.example.austinlieandro_navigationdanapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvProfile.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvProfile.addItemDecoration(itemDecoration)


        val factory: SettingViewModelFactory = SettingViewModelFactory.getInstance(application)
        val settingViewModel: SettingViewModel by viewModels {
            factory
        }

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.person.observe(this){person ->
            setProfileData(person)
        }

        mainViewModel.isLoading.observe(this){loading ->
            showLoading(loading)
        }

        mainViewModel.search.observe(this){search ->
            setSearchData(search)
        }

        binding.searchBar.inflateMenu(R.menu.option_menu)
        binding.searchBar.setOnMenuItemClickListener { menuitem ->
            when(menuitem.itemId){
                R.id.menu1 -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                } else -> false
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val query = searchView.text.toString()
                    mainViewModel.searchUser(query)
                    searchView.hide()
                    false
                }
        }
    }

    private fun setProfileData(person: List<ResponseItem>) {
        val adapter = ProfileAdapter()
        adapter.submitList(person)
        binding.rvProfile.adapter = adapter
    }

    private fun setSearchData(person: List<ItemsItem>) {
        val adapter = SearchAdapter()
        adapter.submitList(person)
        binding.rvProfile.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}