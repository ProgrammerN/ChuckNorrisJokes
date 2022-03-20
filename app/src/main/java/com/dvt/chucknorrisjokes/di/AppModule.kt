package com.dvt.chucknorrisjokes.di

import android.app.Application
import androidx.room.Room
import com.dvt.chucknorrisjokes.app.Constants
import com.dvt.chucknorrisjokes.repository.DefaultJokesRepository
import com.dvt.chucknorrisjokes.repository.JokesRepository
import com.dvt.chucknorrisjokes.retrofit.JokesApiService
import com.dvt.chucknorrisjokes.room.JokesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Provides dependencies to the graph.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(JokesApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideJokesApiService(retrofit: Retrofit): JokesApiService =
        retrofit.create(JokesApiService::class.java)

    @Singleton
    @Provides
    fun provideDefaultJokesRepository(
        apiService: JokesApiService,
        db: JokesDatabase
    ) = DefaultJokesRepository(apiService, db) as JokesRepository

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): JokesDatabase =
        Room.databaseBuilder(application, JokesDatabase::class.java, Constants.DATABASE_NAME)
            .build()
}