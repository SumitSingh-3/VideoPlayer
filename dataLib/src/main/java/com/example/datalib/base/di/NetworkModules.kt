package com.example.datalib.base.di

import com.example.datalib.BuildConfig
import com.example.datalib.utils.RequestMiddleware
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MoshiModule {

    @Singleton
    @Provides
    fun provideMoshiInstance(
        @KotlinJsonAdapterProQualifier jsonAdapter: JsonAdapter.Factory
    ): Moshi = Moshi.Builder()
        .add(jsonAdapter)
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkAdaptersModule {

    @Singleton
    @Provides
    @KotlinJsonAdapterProQualifier
    fun provideKotlinJsonAdapterFactory(): JsonAdapter.Factory = KotlinJsonAdapterFactory()
}

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkConvertersModule {

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(
         moshi: Moshi
    ): Converter.Factory = MoshiConverterFactory.create(moshi)
}

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideProOkHttpClient(
        requestMiddleware: RequestMiddleware,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(requestMiddleware)
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
    }.build()

    companion object {
        const val CONNECTION_TIMEOUT: Long = 100
        const val BASE_URL = "https://api.pexels.com/"
    }

}