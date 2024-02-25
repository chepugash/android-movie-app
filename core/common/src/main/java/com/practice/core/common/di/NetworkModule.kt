package com.practice.core.common.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.practice.core.common.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL = "base_url"
private const val API_KEY = "api_key"
private const val API_VERSION = "api_version"
private const val HEADER_API_KEY = "x-api-key"

private const val LOGGER = "logger"
private const val HEADERS_INTERCEPTOR = "headers_interceptor"

private const val CONTENT_TYPE = "application/json"

private const val TIMEOUT_VALUE = 5L

val networkModule = module {

    single(named(BASE_URL)) {
        BuildConfig.API_ENDPOINT
    }

    single(named(API_KEY)) {
        BuildConfig.API_KEY
    }

    single(named(API_VERSION)) {
        BuildConfig.API_VERSION
    }

    single(named(LOGGER)) {
        provideLoggingInterceptor()
    }

    single(named(HEADERS_INTERCEPTOR)) {
        provideHeadersInterceptor(
            apiKey = get(named(API_KEY)),
        )
    }

    single {
        val json = Json {
            ignoreUnknownKeys = true
        }
        json.asConverterFactory(CONTENT_TYPE.toMediaType())
    }

    single<OkHttpClient> {
        provideOkHttpClient(
            logger = get(named(LOGGER)),
            headersInterceptor = get(named(HEADERS_INTERCEPTOR))
        )
    }

    single<Retrofit> {
        provideRetrofit(
            httpClient = get(),
            converterFactory = get(),
            baseUrl = get(named(BASE_URL))
        )
    }
}

private fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}

private fun provideHeadersInterceptor(
    apiKey: String,
): Interceptor = Interceptor { chain ->
    val original = chain.request()
    val new = original.newBuilder()
        .header(HEADER_API_KEY, apiKey)
        .build()
    chain.proceed(new)
}

private fun provideOkHttpClient(
    logger: Interceptor,
    headersInterceptor: Interceptor
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .addInterceptor(headersInterceptor)
    .connectTimeout(TIMEOUT_VALUE, TimeUnit.SECONDS)
    .build()

private fun provideRetrofit(
    httpClient: OkHttpClient,
    converterFactory: Converter.Factory,
    baseUrl: String
): Retrofit = Retrofit.Builder()
    .client(httpClient)
    .addConverterFactory(converterFactory)
    .baseUrl(baseUrl)
    .build()
