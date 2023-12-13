package com.capstone.yusayhub.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("username")
    var username: String = "",

    @SerializedName("firstName")
    var firstName: String = "",

    @SerializedName("lastName")
    var lastName: String = "",

    @SerializedName("phone")
    var phone: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("status")
    var status: String = "",

)