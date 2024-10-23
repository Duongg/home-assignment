package com.duongdd.homeassignment.core

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duongdd.homeassignment.datasource.remoteDatasource.HandlerException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.reflect.Type


abstract class BaseViewModel<STATE: BaseState>(
    defaultState: STATE
): ViewModel() {

    val viewState: STATE get() = _viewState.value
    private val _viewState = mutableStateOf(defaultState)

    abstract val viewStateCopy: STATE


    protected fun <T> launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend () -> T,
    ): Job {
        return viewModelScope.launch(dispatcher){
            block()
        }
    }
    fun reduce(block: STATE.() -> Unit){
        _viewState.value = viewStateCopy.apply(block)
    }

    protected fun handleError(throwable: Throwable, block: (() -> ErrorModel?)? = null){
        val handleError = block?.invoke() ?: when(throwable){
            is HandlerException.ResponseFailed -> {ErrorModel("Something went wrong","Please try again")}
            is HandlerException.AuthenticationFailed -> {ErrorModel("Authentication Failed", "Please try again")}
            is HandlerException.LostInternetConnection -> {ErrorModel("Internet Connection Error","Please try again")}
            is HandlerException.UnknownException -> {ErrorModel("Something went wrong","Please try again")}
            else -> {ErrorModel("Something went wrong","Please try again")}
        }
        reduce {
            errorMessage = Event(handleError)
        }
    }
}

data class Event<out T>(
    private val content: T,
    private var hasBeenHandled: Boolean = false
){
    fun getContent(): T? {
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}
