package com.duongdd.homeassignment.ui.userDetail

import com.duongdd.homeassignment.datasource.entity.UserDetailsEntity

data class UserDetailsModel(
    val login: String?,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val location: String?,
    val followers: Int = 0,
    val following: Int = 0,
)


fun UserDetailsEntity.toEntity() = UserDetailsModel(
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    location = location,
    followers = followers,
    following = following
)