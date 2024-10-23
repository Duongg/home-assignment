package com.duongdd.homeassignment.ui.userDetail

import com.duongdd.homeassignment.datasource.remoteDatasource.HandlerException
import com.duongdd.homeassignment.datasource.remoteDatasource.repository.UserRepository
import com.duongdd.homeassignment.ui.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Rule
import org.junit.Test


class UserDetailsViewModelTest{
    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    private val userRepository: UserRepository = mockkClass(UserRepository::class)
    private val viewModel = UserDetailsViewModel(
        userRepository,
    )

    @Test
    fun `test get user detail success`(){
        coEvery {
            userRepository.getUserDetail("David")
        } coAnswers {Result.success(mockk(relaxed = true))}
        coroutineTestRule.executeCoroutines()
        assert(viewModel.viewState.errorMessage == null)
    }
    @Test
    fun `test get user detail failed`(){
        coEvery {
            userRepository.getUserDetail("")
        } coAnswers {Result.failure(HandlerException.ResponseFailed)}
        coroutineTestRule.executeCoroutines()
    }

}