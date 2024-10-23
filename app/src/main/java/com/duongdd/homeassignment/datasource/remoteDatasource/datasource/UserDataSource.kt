package com.duongdd.homeassignment.datasource.remoteDatasource.datasource

import com.duongdd.homeassignment.datasource.entity.UserDetailsEntity
import com.duongdd.homeassignment.datasource.entity.UserItemEntity

interface UserDataSource {
    suspend fun getUserList(per_page: Int, since: Int): Result<List<UserItemEntity>>

    suspend fun getUserDetail(username: String): Result<UserDetailsEntity>
}