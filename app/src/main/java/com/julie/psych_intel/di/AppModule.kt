package com.julie.psych_intel.di

import android.content.Context
import com.julie.psych_intel.remote.GrpcService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGrpcService(@ApplicationContext context: Context): GrpcService = GrpcService(context)
}