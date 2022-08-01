package com.sarang.toringlogin

import com.example.torang_core.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface LoginService {
    @FormUrlEncoded
    @POST("facebook_login")
    suspend fun facebook_login(@Field("accessToken") accessToken: String): Response<User>
}