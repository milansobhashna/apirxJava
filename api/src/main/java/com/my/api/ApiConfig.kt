package com.my.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig(val BASE_URL:String) {
    private var retrofit: Retrofit? = null
    val IS_DEBUGGABLE = true

    fun getClient(): Retrofit? {

        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest: Request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .build()

                return chain.proceed(newRequest)
            }
        }

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        if (IS_DEBUGGABLE) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        val client = builder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit
    }

    fun getClientWithHeader(headerToken: String): Retrofit? {

        val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest: Request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", headerToken)
                        .build()

                return chain.proceed(newRequest)
            }
        }

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        if (IS_DEBUGGABLE) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }
        val client = builder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit
    }
}