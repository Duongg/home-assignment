package com.duongdd.homeassignment.core

abstract class BaseState {
    abstract var errorMessage: Event<ErrorModel>?
}

data class ErrorModel(
    val title: String? = null,
    val message: String? = null,
)