package com.dvt.chucknorrisjokes.repository

import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer.
 */
interface JokesRepository {

    fun getRandomJoke(): Flow<Resource<Joke>>

    fun getJokesFromQuery(searchQuery: String): Flow<Resource<List<Joke>>>

    fun getJokesCategories(): Flow<Resource<List<Category>>>

    fun getJokesByCategory(category: String): Flow<Resource<List<Joke>>>
}
