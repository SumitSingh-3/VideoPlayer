package com.example.datalib.data.service.di

import com.example.datalib.data.service.VideosService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object VideosServiceModule {

    @Singleton
    @Provides
    fun provideMostPopularVideosService(
        retrofit : Retrofit
    ) : VideosService = retrofit.create(VideosService::class.java)
}