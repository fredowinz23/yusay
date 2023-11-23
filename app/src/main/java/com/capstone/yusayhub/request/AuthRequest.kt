package com.capstone.yusayhub.request

import com.google.gson.annotations.SerializedName
import com.capstone.yusayhub.models.User

data class AuthRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("success")
    var success: String = "",

    @SerializedName("profile")
    var profile: User? = null
)