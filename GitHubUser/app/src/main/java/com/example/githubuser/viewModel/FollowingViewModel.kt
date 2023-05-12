package com.example.githubuser.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.UserPreviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel {
    private val _userFollowing = MutableLiveData<List<UserPreviewResponse>>()
    val userFollowing : LiveData<List<UserPreviewResponse>> = _userFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowingViewModel"
    }

    fun getUserFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(query)
        client.enqueue(object : Callback<List<UserPreviewResponse>> {
            override fun onResponse(
                call: Call<List<UserPreviewResponse>>,
                response: Response<List<UserPreviewResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
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