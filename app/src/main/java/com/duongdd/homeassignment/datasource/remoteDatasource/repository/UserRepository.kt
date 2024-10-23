package com.duongdd.homeassignment.datasource.remoteDatasource.repository

import com.duongdd.homeassignment.core.CoroutineAware
import com.duongdd.homeassignment.datasource.entity.UserDetailsEntity
import com.duongdd.homeassignment.datasource.entity.UserItemEntity
import com.duongdd.homeassignment.datasource.remoteDatasource.datasource.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource,
    dispatcher: CoroutineDispatcher,
): CoroutineAware(dispatcher) {
    // get user list from api service
    suspend fun getUserList(per_page: Int, since: Int): Result<List<UserItemEntity>> = async {
        userDataSource.getUserList(per_page, since)
    }
    // get user detail from api service
    suspend fun getUserDetail(username: String): Result<UserDetailsEntity> = async {
        userDataSource.getUserDetail(username)
    }
}