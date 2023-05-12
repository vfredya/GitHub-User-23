package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.adapter.SectionPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.viewModel.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var user: UserDetailResponse
    private lateinit var detailViewModel: UserDetailViewModel
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_DATA = "extra_data"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        supportActionBar?.title = user.login
        detailViewModel.isLoading.observe(this, {
            showLoading(it, binding.progressBar)
        })
        detailViewModel.userDetail.observe(this, { listFollowers ->
            setFragment(listFollowers)
        })

        sectionPager()
    }

    private fun setFragment(user: UserDetailResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatar).into(ivAvatar)
            tvUsername.text = user.login
            tvName.text = user.name
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
        }
    }


    private fun sectionPager() {
        val username = intent.getStringExtra(EXTRA_DATA)
        if (username != null) {
            detailViewModel.findUser(username)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}