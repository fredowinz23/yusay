package com.capstone.yusayhub.request

import com.capstone.yusayhub.models.Account
import com.capstone.yusayhub.models.Program
import com.google.gson.annotations.SerializedName

data class ProgramListRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("success")
    var success: String = "",

    @SerializedName("program_list")
    var program_list: List<Program>? = null
)