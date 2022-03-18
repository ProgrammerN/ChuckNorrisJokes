package com.dvt.chucknorrisjokes.retrofit

import com.dvt.chucknorrisjokes.app.Constants
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Interface to the api.
 */

interface JokesApiService {

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }

    /**
     * gets random joke.
     *
     * @return random joke.
     */
    @GET("random")
    suspend fun getRandomJoke(): Joke

    /**
     * gets jokes from a provide query.
     *
     * @param query search text.
     */
    @GET("search")
    suspend fun getJokesByQuery(@Query("query") query: String): SearchResult

    /**
     * gets a random joke from a provide category.
     *
     * @param category category.
     */
    @GET("random")
    suspend fun getJokeByCategory(@Query("category") category: String): Joke

    /**
     * gets all categories.
     *
     */
    @GET("categories")
    suspend fun getJokesCategories(): List<String>
}