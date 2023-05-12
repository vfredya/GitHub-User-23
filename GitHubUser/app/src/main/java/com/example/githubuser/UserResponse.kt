package com.example.githubuser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val userPreviewResponse: List<UserPreviewResponse>
) : Parcelable

@Parcelize
data class UserPreviewResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatar: String,
    ) : Parcelable

@Parcelize
data class UserDetailResponse(
    @field:SerializedName("avatar_url")
    val avatar: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("following")
    val following: Int? = null
) : Parcelable
