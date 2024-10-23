package com.duongdd.homeassignment.datasource.remoteDatasource.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("status_message" ) var statusMessage : String?  = null,
    @SerializedName("success"        ) var success       : Boolean? = null
)