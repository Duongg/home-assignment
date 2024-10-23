package com.duongdd.homeassignment.datasource.remoteDatasource.api

import com.duongdd.homeassignment.datasource.remoteDatasource.response.UserDetailsResponse
import com.duongdd.homeassignment.datasource.remoteDatasource.response.UserItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {

    @GET("users")
    suspend fun getUserList(@Query("per_page") page: Int, @Query("since") since: Int ): Response<List<UserItemResponse>>

    @GET("users/{login_username}")
    suspend fun getUserDetail(@Path("login_username") loginUsername: String): Response<UserDetailsResponse>
}