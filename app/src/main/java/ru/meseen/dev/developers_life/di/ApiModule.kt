package ru.meseen.dev.developers_life.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.meseen.dev.developers_life.BuildConfig
import ru.meseen.dev.developers_life.data.api.ConnectionObserver
import ru.meseen.dev.developers_life.data.api.DevLifeService
import java.util.concurrent.TimeUnit

/**
 * @author Vyacheslav Doroshenko
 */
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val contentType = "application/json".toMediaType()

    @ExperimentalSerializationApi
    private val converter = json.asConverterFactory(contentType)

    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(JsonInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @ExperimentalSerializationApi
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(converter)
            .build()

    @Provides
    fun provideService(retrofit: Retrofit): DevLifeService =
        retrofit.create(DevLifeService::class.java)

    @Provides
    fun provideConnectionObserver(@ApplicationContext applicationContext: Context): ConnectionObserver =
        ConnectionObserver(applicationContext)


}

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