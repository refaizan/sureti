package com.sureti.network

import com.sureti.models.response.ActiveCheckResponseModel
import com.sureti.models.response.ParentResponseModel
import com.sureti.models.response.SignInResponseModel
import com.sureti.models.response.SignUpResponseModel
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query


interface APIService {

    @POST("doesUserExist")
    fun doesUserExist(@Query("email") email: String): Call<ParentResponseModel>

    @POST("UserLogin")
    fun signIn(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<SignInResponseModel>

    @POST("RegisterUser")
    fun signUp(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String,
        @Query("userCellNo") cellNo: String,
        @Query("mailingAddress") address: String
    ): Call<SignUpResponseModel>

    @POST("getActiveChecks")
    fun getActiveChecks(
        @Query("token") token: String
    ): Call<ActiveCheckResponseModel>
}