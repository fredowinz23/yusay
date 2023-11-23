package com.capstone.yusayhub.request

import com.capstone.yusayhub.models.Account
import com.capstone.yusayhub.models.Program
import com.google.gson.annotations.SerializedName

data class JoinProgramRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("programId")
    var programId: Int,

    @SerializedName("success")
    var success: String = "",

    @SerializedName("response")
    var response: String = "",
)