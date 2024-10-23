package com.duongdd.homeassignment.datasource.localDatasource

import com.duongdd.homeassignment.datasource.entity.UserItemEntity
import com.duongdd.homeassignment.datasource.remoteDatasource.RetrofitDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    dispatcher: CoroutineDispatcher
) : RetrofitDataSource(coroutineDispatcher = dispatcher), LocalRepository{

    // save data to local database
    override suspend fun saveUserInfo(userItemEntity: UserItemEntity) = async {
        userDao.insertUserList(userItemEntity)
    }

    // get data from local database
    override suspend fun getUserInfoList(): List<UserItemEntity> = async {
         userDao.getUserList()
    }
}