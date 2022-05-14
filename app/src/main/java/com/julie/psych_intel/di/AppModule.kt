package com.julie.psych_intel.di

import android.content.Context
import com.julie.psych_intel.data.remote.GrpcService
import com.julie.psych_intel.repositories.ChatroomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGrpcService(@ApplicationContext context: Context): GrpcService = GrpcService(context)

    @Singleton
    @Provides
    fun provideChatroomRepository(grpcService: GrpcService) = ChatroomRepository(grpcService)
}