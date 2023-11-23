package com.capstone.yusayhub.api

import com.capstone.yusayhub.request.AuthRequest
import com.capstone.yusayhub.request.JoinProgramRequest
import com.capstone.yusayhub.request.ProgramListRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers


interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("api/login.php")
    fun loginUser(@Body loginInfo: AuthRequest): Call<AuthRequest>

    @Headers("Content-Type: application/json")
    @POST("api/change-password.php")
    fun changePassword(@Body loginInfo: AuthRequest): Call<AuthRequest>

    @Headers("Content-Type: application/json")
    @POST("api/get-programs.php")
    fun getPrograms(@Body programListRequest: ProgramListRequest): Call<ProgramListRequest>

    @Headers("Content-Type: application/json")
    @POST("api/join-program.php")
    fun joinProgram(@Body joinProgramRequest: JoinProgramRequest): Call<JoinProgramRequest>

    @Headers("Content-Type: application/json")
    @POST("api/unjoin-program.php")
    fun unjoinProgram(@Body joinProgramRequest: JoinProgramRequest): Call<JoinProgramRequest>

    @Headers("Content-Type: application/json")
    @POST("api/get-joined-programs.php")
    fun getJoinedPrograms(@Body programListRequest: ProgramListRequest): Call<ProgramListRequest>

}