package com.dvt.chucknorrisjokes.api

import com.dvt.chucknorrisjokes.data.Joke
import com.dvt.chucknorrisjokes.data.SearchResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokesApiService {

    companion object {
        const val BASE_URL = "https://api.chucknorris.io/jokes/"
    }

    @GET("random")
    suspend fun getRandomJoke(): Joke

    @GET("search")
    suspend fun getJokesByQuery(@Query("query") query: String): Flow<SearchResult>

    @GET("random?category={category}")
    suspend fun getJokeByCategory(@Path("category") category: String): Flow<List<Joke>>

    @GET("categories")
    suspend fun getJokesCategories(): List<Joke>
}