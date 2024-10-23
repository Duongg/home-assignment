package com.duongdd.homeassignment.ui.userList

import com.duongdd.homeassignment.datasource.entity.UserItemEntity

data class UserModel(
    val imageUrl: String = "",
    val login: String = "",
    val htmlUrl: String = ""
)

fun UserItemEntity.toModel() = UserModel(
    imageUrl = avatarUrl ?: "",
    login = login ?: "",
    htmlUrl = htmlUrl ?: ""
)