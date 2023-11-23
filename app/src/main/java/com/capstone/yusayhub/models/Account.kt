package com.capstone.yusayhub.models

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("income")
    var income: Float = 0f,

    @SerializedName("expense")
    var expense: Float = 0f,

    @SerializedName("balance")
    var balance: Float = 0f,

)