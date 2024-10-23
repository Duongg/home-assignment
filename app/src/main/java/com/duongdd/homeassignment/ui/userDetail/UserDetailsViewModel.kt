package com.duongdd.homeassignment.ui.userDetail

import com.duongdd.homeassignment.core.BaseViewModel
import com.duongdd.homeassignment.datasource.remoteDatasource.datasource.UserDataSource
import com.duongdd.homeassignment.datasource.remoteDatasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor (
    private val userRepository: UserRepository,
): BaseViewModel<UserDetailsState>(UserDetailsState()){
    override val viewStateCopy: UserDetailsState = viewState.copy()

    fun onUiEvent(uiEvent: UserDetailsUiEvent){
        when(uiEvent){
            is UserDetailsUiEvent.UserDetailsLoaded -> handleUserDetailsLoaded(uiEvent)
        }
    }

    // handle to get user detail when user details screen is loaded
    private fun handleUserDetailsLoaded(uiEvent: UserDetailsUiEvent.UserDetailsLoaded) = launch {
        userRepository.getUserDetail(uiEvent.username)
            .onSuccess { response ->
                reduce {
                    userDetail = response.toEntity()
                }
            }
            .onFailure {
                handleError(it)
            }
    }
}