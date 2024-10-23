package com.duongdd.homeassignment.ui.userList

import com.duongdd.homeassignment.datasource.entity.UserItemEntity
import com.duongdd.homeassignment.datasource.localDatasource.LocalRepository
import com.duongdd.homeassignment.datasource.remoteDatasource.HandlerException
import com.duongdd.homeassignment.datasource.remoteDatasource.repository.UserRepository
import com.duongdd.homeassignment.ui.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UserListViewModelTest{
    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    private val userRepository: UserRepository = mockkClass(UserRepository::class)
    private val localRepository: LocalRepository = mockkClass(LocalRepository::class)
    private val viewModel = UserListViewModel(
        userRepository,
        localRepository
    )

    @Test
    fun `test get user info list success`() = runTest {
        coEvery {
            userRepository.getUserList(20, 0)
        } coAnswers {Result.success(mockk(relaxed = true))}
        coroutineTestRule.executeCoroutines()
        assert(viewModel.viewState.errorMessage == null)
    }

    @Test
    fun `test get user info list failed`() = runTest {
        coEvery {
            userRepository.getUserList(20, 0)
        } coAnswers {Result.failure(HandlerException.ResponseFailed)}
        coroutineTestRule.executeCoroutines()
    }
}