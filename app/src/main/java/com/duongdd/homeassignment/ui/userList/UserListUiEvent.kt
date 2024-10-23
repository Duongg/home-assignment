package com.duongdd.homeassignment.ui.userList

sealed class UserListUiEvent {

    object UserListLoaded : UserListUiEvent()

    data class ItemScrolled(val index: Int): UserListUiEvent()
}