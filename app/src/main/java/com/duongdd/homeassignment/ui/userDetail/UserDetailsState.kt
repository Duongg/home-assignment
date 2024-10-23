package com.duongdd.homeassignment.ui.userDetail

import com.duongdd.homeassignment.core.BaseState
import com.duongdd.homeassignment.core.ErrorModel
import com.duongdd.homeassignment.core.Event

data class UserDetailsState(
    var userDetail: UserDetailsModel? = null,
    override var errorMessage: Event<ErrorModel>? = null,
): BaseState()