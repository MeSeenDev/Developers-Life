package ru.meseen.dev.tinkofflab_0.model.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object DevApi {

    //https://developerslife.ru/latest/1?json=true&pageSize=1&types=gif
    private const val BASE_URL = "https://developerslife.ru/"
    const val START_PAGE = 0

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
    private val contentType = "application/json".toMediaType()
    private val converter = json.asConverterFactory(contentType)

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(JsonInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .build()

    val service: DevLifeService = retrofit.create(DevLifeService::class.java)


    private class JsonInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val orReq = chain.request()
            val orUrl = orReq.url

            val url = orUrl.newBuilder()
                .addQueryParameter("json", "true")
                .build()

            val request = orReq
                .newBuilder()
                .url(url)
                .build()

            return chain.proceed(request)

        }

    }

    enum class SectionType(val selection: String) {
        LATEST("latest"), HOT("hot"), TOP("top")
    }
}