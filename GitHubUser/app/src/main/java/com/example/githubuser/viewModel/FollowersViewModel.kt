package com.example.githubuser.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.UserPreviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel {
    private val _userFollowers = MutableLiveData<List<UserPreviewResponse>>()
    val userFollowers : LiveData<List<UserPreviewResponse>> = _userFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowersViewModel"
    }

    fun getUserFollowers(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object : Callback<List<UserPreviewResponse>> {
            override fun onResponse(
                call: Call<List<UserPreviewResponse>>,
                response: Response<List<UserPreviewResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserPreviewResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}