package com.duongdd.homeassignment.core.di

import android.app.Application
import androidx.room.Room
import com.duongdd.homeassignment.datasource.localDatasource.UserDao
import com.duongdd.homeassignment.datasource.localDatasource.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDataBase {
        return Room.databaseBuilder(
            app,
            UserDataBase::class.java,
            UserDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(userDataBase: UserDataBase): UserDao {
        return userDataBase.userDao()
    }
}