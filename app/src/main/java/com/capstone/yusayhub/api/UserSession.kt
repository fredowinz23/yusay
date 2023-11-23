package com.capstone.yusayhub.api

import android.content.Context

class UserSession(context: Context?) {
    val prefs = context?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val username = prefs?.getString("username", "")
    val BASE_URL = prefs?.getString("base_url", "http://10.0.2.2/")
    val edit = prefs?.edit()
}