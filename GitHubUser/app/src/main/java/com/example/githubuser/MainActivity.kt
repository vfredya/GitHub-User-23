package com.example.githubuser

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.githubuser.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User"

        mainViewModel.userPreview.observe(this, { userPreview ->
            showRecyclerList(userPreview)
        })
        mainViewModel.isLoading.observe(this, {
            showLoading(it, binding.progressBar)
        })
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUsers.layoutManager = layoutManager
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    Toast.makeText(this@MainActivity, R.string.not_connected, Toast.LENGTH_LONG).show()
                } else {
                    searchView.clearFocus()
                    binding.rvUsers.visibility = View.VISIBLE
                    mainViewModel.findUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun showCount(totalCount: Int) {
        with(binding) {
            if (totalCount == 0) {
                rvUsers.visibility = View.VISIBLE
//                rvUsers.text = resources.getString(R.string.not_connected)
            } else {
                rvUsers.visibility = View.INVISIBLE
            }
        }
    }

    private fun showRecyclerList(list:List<UserPreviewResponse>) : ListUserAdapter {
//        val listUsers = ArrayList<UserPreviewResponse>()
//
//        list?.let {
//            listUsers.addAll(it)
//        }
//
//        return ListUserAdapter(listUsers)
        val listUser = ArrayList<UserResponse>()
        for (user in listGithubUser) {
            listUser.clear()
            listUser.addAll(listGithubUser)
        }
        val adapter = SearchAdapter(listUser)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(list: UserPreviewResponse) {
        val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_DATA, list)
        startActivity(moveIntent)
    }

    fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}
