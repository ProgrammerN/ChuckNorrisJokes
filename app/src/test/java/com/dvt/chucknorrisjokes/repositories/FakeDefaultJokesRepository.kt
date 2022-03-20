package com.dvt.chucknorrisjokes.repositories

import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.repository.JokesRepository
import com.dvt.chucknorrisjokes.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeDefaultJokesRepository() : JokesRepository {

    override fun getRandomJoke(): Flow<Resource<Joke>> {
        TODO("Not yet implemented")
    }

    override fun getJokesFromQuery(searchQuery: String): Flow<Resource<List<Joke>>> {
        TODO("Not yet implemented")
    }

    override fun getJokesCategories(): Flow<Resource<List<Category>>> {
        TODO("Not yet implemented")
    }

    override fun getJokesByCategory(category: String): Flow<Resource<List<Joke>>> {
        TODO("Not yet implemented")
    }

}











