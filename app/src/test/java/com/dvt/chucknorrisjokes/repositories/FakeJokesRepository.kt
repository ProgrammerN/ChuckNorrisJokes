package com.dvt.chucknorrisjokes.repositories

import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.repository.JokesRepository
import com.dvt.chucknorrisjokes.retrofit.JokesApiService
import com.dvt.chucknorrisjokes.room.JokesDatabase
import com.dvt.chucknorrisjokes.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeJokesRepository(apiService: JokesApiService, db: JokesDatabase) : JokesRepository(apiService, db) {


    /*override fun getRandomJoke(): Flow<Resource<Joke>> {

    }

    override fun getJokesFromQuery(searchQuery: String): Flow<Resource<List<Joke>>> {

    }

    override fun getJokesCategories(): Flow<Resource<List<Category>>> {

    }

    override fun getJokesByCategory(category: String): Flow<Resource<List<Joke>>> {

    }*/
}











