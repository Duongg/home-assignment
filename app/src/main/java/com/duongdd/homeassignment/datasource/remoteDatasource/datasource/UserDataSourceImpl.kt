package com.duongdd.homeassignment.datasource.remoteDatasource.datasource

import com.duongdd.homeassignment.datasource.entity.UserDetailsEntity
import com.duongdd.homeassignment.datasource.entity.UserItemEntity
import com.duongdd.homeassignment.datasource.entity.toEntity
import com.duongdd.homeassignment.datasource.remoteDatasource.RetrofitDataSource
import com.duongdd.homeassignment.datasource.remoteDatasource.api.UserAPI
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDataSourceImpl @Inject constructor(
    private val userAPI: UserAPI,
     dispatcher: CoroutineDispatcher
): RetrofitDataSource(coroutineDispatcher = dispatcher),UserDataSource {

    // get User Info list from API service
    override suspend fun getUserList(per_page: Int, since: Int): Result<List<UserItemEntity>> {
        return apiCallResArrayObject {
            userAPI.getUserList(page = per_page, since = since)
        }.map { it.map { it.toEntity() } }
    }

    // get User detail from API service
    override suspend fun getUserDetail(username: String): Result<UserDetailsEntity> {
        return apiCallResObject {
            userAPI.getUserDetail(loginUsername = username)
        }.map{it.toEntity()}
    }
}