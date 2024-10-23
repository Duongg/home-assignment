package com.duongdd.homeassignment.datasource.remoteDatasource.response

import com.google.gson.annotations.SerializedName


data class UserItemResponse(
    @SerializedName("login"          ) var login         : String,
    @SerializedName("avatar_url"       ) var avatarUrl      : String? = null,
    @SerializedName("html_url"   ) var htmlUrl   : String?               = null,
)

data class UserDetailsResponse(
    @SerializedName("login"               ) var login             : String?  = null,
    @SerializedName("avatar_url"          ) var avatarUrl         : String?  = null,
    @SerializedName("html_url"            ) var htmlUrl           : String?  = null,
    @SerializedName("location"            ) var location          : String?  = null,
    @SerializedName("followers"           ) var followers         : Int = 0,
    @SerializedName("following"           ) var following         : Int = 0,
)