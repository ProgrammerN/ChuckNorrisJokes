package com.dvt.chucknorrisjokes.repositories

import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class FakeDataSource(private var jokes: List<Joke>, private var categories: List<Category>) {


    fun queryRandomJoke(): Flow<Joke> {
        val queryJoke: Flow<Joke> = flow {
            while (true) {
                emit(jokes.first()) // Emits the result of the request to the flow
            }
        }
        return queryJoke
    }


    fun getJokesFromQuery(queryText: String): Flow<List<Joke>> {
        val queryJokes: Flow<List<Joke>> = flow {
            while (true) {
                emit(jokes)
            }
        }.mapLatest { plantList ->
            plantList.filter {
                it.value.contains(queryText)
            }
        }
        return queryJokes
    }


    val queryCategories: Flow<List<Category>> = flow {
        while (true) {
            emit(categories)
        }
    }


    fun queryJokesByCategory(queryText: String): Flow<List<Joke>> {
        val categoryJokes: Flow<List<Joke>> = flow {
            while (true) {
                emit(jokes)
            }
        }.map { plantList ->
            plantList.filter {
                it.categories.contains(queryText)
            }
        }
        return categoryJokes
    }

}