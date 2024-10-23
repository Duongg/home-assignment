package com.duongdd.homeassignment.ui.userList

import com.duongdd.homeassignment.core.BaseViewModel
import com.duongdd.homeassignment.datasource.entity.UserItemEntity
import com.duongdd.homeassignment.datasource.localDatasource.LocalRepository
import com.duongdd.homeassignment.datasource.remoteDatasource.datasource.UserDataSource
import com.duongdd.homeassignment.datasource.remoteDatasource.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val localRepository: LocalRepository,
): BaseViewModel<UserListState>(UserListState()){
    override val viewStateCopy: UserListState = viewState.copy()
    private var lastScrolledItem = -1
    private var nextPage = 0
    private var paginationJob: Job? = null
    private var PAGE_SIZE = 20

    fun onUiEvent(uiEvent: UserListUiEvent){
        when(uiEvent){
            is UserListUiEvent.UserListLoaded -> handleUserList()
            is UserListUiEvent.ItemScrolled -> handleScrollItem(uiEvent)
        }
    }

    // handle scroll event to get new user info from api service
    private fun handleScrollItem(uiEvent: UserListUiEvent.ItemScrolled) = launch{
        if(lastScrolledItem >= uiEvent.index) return@launch
        lastScrolledItem = uiEvent.index
        val threshold = (viewState.userList?.size ?: 0) - PAGE_SIZE/2
        if(threshold <= lastScrolledItem){
            if(paginationJob == null){
                paginationJob = handleUserList()
            }
        }
    }


    private fun handleUserList() = launch {
        // check has new item
        checkItemInList()
        // if is check = true -> has no new item -> get user list from local database to display
        // else -> get from api service to display
        if(viewState.isCheck && viewState.userList != null){
            getUserInfoList()
        } else {
            getUserList()
        }
    }

    // get user list from api service
    private fun getUserList() = launch {
        userRepository.getUserList(PAGE_SIZE, nextPage)
            .onSuccess { response ->
                nextPage = nextPage.plus(19)
                reduce {
                    userList = response.map { it.toModel() }
                    // save user info to local database
                    response.forEach { item -> saveUserInfo(item) }
                }
            }.onFailure {
                handleError(it)
            }
        paginationJob = null
    }

    // save user item to local database
    private fun saveUserInfo(userItemEntity: UserItemEntity)= launch {
        localRepository.saveUserInfo(userItemEntity)
    }


    // get user info list from local database
    private fun getUserInfoList()  = launch {
        val listUserInfo = localRepository.getUserInfoList()
        reduce {
            userList = listUserInfo.map { it.toModel() }
        }
    }

    private fun checkItemInList()  = launch{
        withContext(Dispatchers.IO) {
            // get user list from local database
            viewState.userListEntity = localRepository.getUserInfoList()
            // check if user list is null -> get user list from api service
            if(viewState.userList.isNullOrEmpty()){
                getUserList()
            }
            // check if user list from local and user list from api service has new elements
            viewState.isCheck = viewState.userListEntity?.toSet() == viewState.userList?.toSet()
        }
    }
}