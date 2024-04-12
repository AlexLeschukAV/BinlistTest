package com.example.binlisttest.di

import android.content.Context
import com.example.binlisttest.presentation.SharedPrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPrefUtils(@ApplicationContext context: Context): SharedPrefUtils{
        return SharedPrefUtils(context)
    }
}