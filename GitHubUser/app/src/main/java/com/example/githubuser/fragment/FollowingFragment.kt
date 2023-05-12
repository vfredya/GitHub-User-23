package com.example.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubuser.DetailActivity
import com.example.githubuser.R
import com.example.githubuser.UserPreviewResponse
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.viewModel.FollowingViewModel

class FollowingFragment : Fragment() {
    private val username: String? = null
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        const val ARG_USERNAME = "username"
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            username = it.getString(ARG_USERNAME)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it, binding.progressBar)
        })
        followingViewModel.userFollowing.observe(viewLifecycleOwner, { listFollowing ->
            setFragment(listFollowing)
        })

        followingViewModel.getUserFollowing(
            arguments?.getString(DetailActivity.EXTRA_DATA).toString()
        )
    }

    private fun setFragment(listFollower: List<UserPreviewResponse>): ListUserAdapter {
        val listUsers = ArrayList<UserPreviewResponse>()

        listFollower?.let {
            listUsers.addAll(it)
        }

        return ListUserAdapter(listUsers)
    }

    fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}