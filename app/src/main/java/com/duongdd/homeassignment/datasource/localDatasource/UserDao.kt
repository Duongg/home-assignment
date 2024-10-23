package com.duongdd.homeassignment.datasource.localDatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duongdd.homeassignment.datasource.entity.UserItemEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertUserList(userItemEntity: UserItemEntity)

    @Query("SELECT * FROM user_info")
     fun getUserList() : List<UserItemEntity>

}