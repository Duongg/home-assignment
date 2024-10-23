package com.duongdd.homeassignment.datasource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duongdd.homeassignment.datasource.remoteDatasource.response.UserDetailsResponse
import com.duongdd.homeassignment.datasource.remoteDatasource.response.UserItemResponse



@Entity(tableName = "user_info")
data class UserItemEntity(
     @PrimaryKey val login: String,
    val avatarUrl: String?,
    val htmlUrl: String?
)
data class UserDetailsEntity(
    val login: String?,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val location: String?,
    val followers: Int = 0,
    val following: Int = 0,
)


fun UserItemResponse.toEntity() = UserItemEntity(
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
)

fun UserDetailsResponse.toEntity() = UserDetailsEntity(
    login = login,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    location = location,
    followers = followers,
    following = following
)