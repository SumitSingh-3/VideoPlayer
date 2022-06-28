package com.example.datalib.domain.repositories.history.di

import com.example.datalib.domain.repositories.history.VideoHistoryRepo
import com.example.datalib.domain.repositories.history.VideoHistoryRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class VideoHistoryRepoModule {

    @Binds
    abstract fun bindVideoHistoryRepo(repoImpl: VideoHistoryRepoImpl) : VideoHistoryRepo
}