package com.capstone.yusayhub.models

import com.google.gson.annotations.SerializedName

data class Program(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("date")
    var date: String = "",

    @SerializedName("time")
    var time: String = "",

    @SerializedName("description")
    var description: String = "",

    @SerializedName("category")
    var category: Category? = null,

    @SerializedName("address")
    var address: String = "",

    @SerializedName("notes")
    var notes: String = "",

    @SerializedName("maxVolunteer")
    var maxVolunteer: Int = 0,

    @SerializedName("amountSpent")
    var amountSpent: Float = 0f,

    @SerializedName("status")
    var status: String = "",

    @SerializedName("image")
    var image: String = "",

    @SerializedName("totalJoiner")
    var totalJoiner: Int = 0,

)