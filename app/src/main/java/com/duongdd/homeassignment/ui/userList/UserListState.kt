package com.duongdd.homeassignment.ui.userList

import com.duongdd.homeassignment.core.BaseState
import com.duongdd.homeassignment.core.ErrorModel
import com.duongdd.homeassignment.core.Event
import com.duongdd.homeassignment.datasource.entity.UserItemEntity

data class UserListState(
    var userList: List<UserModel>? = mutableListOf(),
    var isCheck: Boolean = false,
    var userListEntity: List<UserItemEntity>? = mutableListOf(),
    override var errorMessage: Event<ErrorModel>? = null,
): BaseState()