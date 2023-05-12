package com.example.githubuser.api

import com.example.githubuser.UserDetailResponse
import com.example.githubuser.UserPreviewResponse
import com.example.githubuser.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Path("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String):
            Call<List<UserPreviewResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String):
            Call<List<UserPreviewResponse>>
}