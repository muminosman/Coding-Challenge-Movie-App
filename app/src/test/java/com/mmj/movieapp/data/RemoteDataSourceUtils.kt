package com.mmj.movieapp.data

import com.mmj.movieapp.core.network.MovieApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

internal fun MockWebServer.enqueueResponse(body: String, code: Int) {
        val response = MockResponse()
            .setResponseCode(code)
            .setBody(body)
        enqueue(response)
}

internal fun initMockWebserver(): MockWebServer = MockWebServer()

internal fun initMockOkHttpClient(): OkHttpClient = OkHttpClient()
    .newBuilder()
    .build()


internal fun initMockRetrofit(
    mockWebServer: MockWebServer,
    okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
    .baseUrl(mockWebServer.url("/"))
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

internal fun initMoviesApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
