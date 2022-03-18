package com.dvt.chucknorrisjokes.api

import com.dvt.chucknorrisjokes.app.Constants
import com.dvt.chucknorrisjokes.data.Joke
import com.dvt.chucknorrisjokes.data.SearchResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit Interface to the api.
 */

interface JokesApiService {

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }

    @GET("random")
    suspend fun getRandomJoke(): Joke

    @GET("search")
    suspend fun getJokesByQuery(@Query("query") query: String): SearchResult

    @GET("random")
    suspend fun getJokeByCategory(@Query("category") category: String): SearchResult

    @GET("categories")
    suspend fun getJokesCategories(): List<String>
}