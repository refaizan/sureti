package com.sureti.network

import com.sureti.models.response.ActiveCheckResponseModel
import com.sureti.models.response.ParentResponseModel
import com.sureti.models.response.SignInResponseModel
import com.sureti.models.response.SignUpResponseModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    var BASE_URL = "https://devconsole.sureti.com:9000/Mobile/"
    private var apiService: APIService? = null
    private fun init(): APIService {

        val builder = OkHttpClient.Builder()

        builder.readTimeout(1, TimeUnit.MINUTES)
        builder.writeTimeout(1, TimeUnit.MINUTES)
        builder.connectTimeout(1, TimeUnit.MINUTES)

        builder.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Accept", "application/json")
            chain.proceed(requestBuilder.build())
        })

        val client = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(APIService::class.java)
    }

    private val apiInstance: APIService?
        get() {
            if (apiService == null) apiService = init()
            return apiService
        }

    fun isEmailExist(email: String, callback: Callback<ParentResponseModel>) {
        if (apiService == null) apiService = apiInstance
        apiService!!.doesUserExist(email).enqueue(callback)
    }

    fun signIn(email: String, password: String, callback: Callback<SignInResponseModel>) {
        if (apiService == null) apiService = apiInstance
        apiService!!.signIn(email, password).enqueue(callback)
    }

    fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        callback: Callback<SignUpResponseModel>
    ) {
        if (apiService == null) apiService = apiInstance
        apiService!!.signUp(
            email,
            password,
            firstName,
            lastName,
            "+12090078601",
            "New york, Street III ,House No 780"
        ).enqueue(callback)
    }

    fun getActiveChecks(token: String, callback: Callback<ActiveCheckResponseModel>) {
        if (apiService == null) apiService = apiInstance
        apiService!!.getActiveChecks(token).enqueue(callback)
    }

}