package com.duongdd.homeassignment.datasource.localDatasource

import com.duongdd.homeassignment.datasource.entity.UserItemEntity

interface LocalRepository {
    suspend fun saveUserInfo(userItemEntity: UserItemEntity)

    suspend fun getUserInfoList(): List<UserItemEntity>
}