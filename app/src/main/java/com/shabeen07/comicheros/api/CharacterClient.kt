package com.shabeen07.comicheros.api

import com.shabeen07.comicheros.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CharacterClient {

    // retrofit builder
    private val retrofitBuilder : Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
    }

    // okhttp client
    private val client: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
    }

    // api service
    val characterService: CharacterService by lazy {
        retrofitBuilder.build()
            .create(CharacterService::class.java)
    }

}