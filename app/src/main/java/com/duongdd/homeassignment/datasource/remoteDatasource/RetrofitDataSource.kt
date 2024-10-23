package com.duongdd.homeassignment.datasource.remoteDatasource

import com.duongdd.homeassignment.core.CoroutineAware
import com.duongdd.homeassignment.datasource.remoteDatasource.response.ErrorResponse
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Response


abstract class RetrofitDataSource(
    coroutineDispatcher: CoroutineDispatcher
): CoroutineAware(coroutineDispatcher) {
    private val errorMapper = ErrorMapper()

    suspend fun <T> apiCallResArrayObject(call: suspend () -> Response<List<T>>): Result<List<T>>{
        return try {
            val response = call.invoke()
            if(response.isSuccessful){
                Result.success((response.body())!!)
            }else{
                errorMapper.mapApiError(response)
                Result.failure(Throwable())
            }
        }catch (t: Throwable){
            Result.failure(t)
        }
    }
    suspend fun <T> apiCallResObject(call: suspend () -> Response<T>): Result<T>{
        return try {
            val response = call.invoke()
            if(response.isSuccessful){
                Result.success((response.body())!!)
            }else{
                errorMapper.mapApiError(response)
                Result.failure(Throwable())
            }
        }catch (t: Throwable){
            Result.failure(t)
        }
    }
    class ErrorMapper{
        private val jsonParser = Json { ignoreUnknownKeys = true}
        fun mapApiError(response: Response<*>): HandlerException {
            return try {
                val errorData = response.errorBody()?.byteStream()?.bufferedReader()?.readText() ?: "{}"
                val errorModel: ErrorResponse = jsonParser.decodeFromString(errorData)
                mapApiError(errorModel)
            } catch (e:Exception) {
                HandlerException.UnknownException
            }
        }
        private fun mapApiError(errorResponse: ErrorResponse): HandlerException{
            return when(errorResponse.statusCode){
                401 ->  HandlerException.AuthenticationFailed
                500 -> HandlerException.ResponseFailed
                else ->HandlerException.UnknownException
            }
        }
    }
}

open class HandlerException(message: String? = null): Exception(message){
    object LostInternetConnection: HandlerException()

    object AuthenticationFailed: HandlerException()

    object ResponseFailed: HandlerException()

    object UnknownException: HandlerException()
}