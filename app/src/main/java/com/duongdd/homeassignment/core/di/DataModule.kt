package com.duongdd.homeassignment.core.di

import android.content.Context
import com.duongdd.homeassignment.datasource.localDatasource.LocalRepository
import com.duongdd.homeassignment.datasource.localDatasource.LocalRepositoryImpl
import com.duongdd.homeassignment.datasource.remoteDatasource.datasource.UserDataSource
import com.duongdd.homeassignment.datasource.remoteDatasource.datasource.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindUserDataSource(userDataSource: UserDataSourceImpl) : UserDataSource

    @Binds
    abstract fun bindLocalRepository(localRepository: LocalRepositoryImpl): LocalRepository

    @Binds
    abstract fun bindAppContext(@ApplicationContext context: Context): Context


}