package com.duongdd.homeassignment.ui.userDetail

sealed class UserDetailsUiEvent {
    class UserDetailsLoaded(val username: String) : UserDetailsUiEvent()
}