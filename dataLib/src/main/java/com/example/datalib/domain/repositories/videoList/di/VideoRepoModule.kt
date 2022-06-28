package com.example.datalib.domain.repositories.videoList.di

import com.example.datalib.domain.repositories.videoList.VideoRepo
import com.example.datalib.domain.repositories.videoList.VideoRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class VideoRepoModule {

    @Binds
    abstract fun bindTopVideoRepoModule(repoImpl: VideoRepoImpl): VideoRepo
}