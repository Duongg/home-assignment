package com.duongdd.homeassignment.datasource.localDatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duongdd.homeassignment.datasource.entity.UserItemEntity
@Database(
    entities = [UserItemEntity::class],
    version = 1
)
abstract class UserDataBase: RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "users_db"
    }
}

