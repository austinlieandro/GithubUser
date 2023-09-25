package com.example.austinlieandro_navigationdanapi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.austinlieandro_navigationdanapi.data.response.ResponseItem
import com.example.austinlieandro_navigationdanapi.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val followersViewModel by viewModels<FollowersViewModel>()

    private var position: Int = 0
    private var username: String? = null

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(EXTRA_USER)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        followersViewModel.isLoading.observe(viewLifecycleOwner){loading ->
            showLoading(loading)
        }

        if (username != null) {
            followersViewModel.getFollowers(username!!)

            followersViewModel.followers.observe(viewLifecycleOwner) { followers ->
                setProfileData(followers)
            }

        }
    }

    private fun setProfileData(person: List<ResponseItem>) {
        val adapter = FollowersAdapter()
        adapter.submitList(person)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) { binding.progressBar3.visibility = if (state) View.VISIBLE else View.GONE }
}