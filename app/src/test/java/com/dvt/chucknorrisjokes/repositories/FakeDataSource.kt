package com.dvt.chucknorrisjokes.repositories

import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataSource(private var jokes: List<Joke>, private var categories: List<Category>) {

    val queryJoke: Flow<Joke> = flow {
        while (true) {
            emit(jokes.first()) // Emits the result of the request to the flow
        }
    }

    val queryJokes: Flow<List<Joke>> = flow {
        while (true) {
            emit(jokes) // Emits the result of the request to the flow
        }
    }

    val queryCategories: Flow<List<Category>> = flow {
        while (true) {
            emit(categories)
        }
    }

    val queryJokesByCategory: Flow<List<Joke>> = flow {
        while (true) {
            emit(jokes)
        }
    }

}