package com.dvt.chucknorrisjokes.repositories

import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.repository.JokesRepository
import com.dvt.chucknorrisjokes.util.Resource
import com.dvt.chucknorrisjokes.util.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FakeDefaultJokesRepository(private val fakeDataSource: FakeDataSource) : JokesRepository {

    override fun getRandomJoke() = networkBoundResource(
        query = {
            fakeDataSource.queryJoke
        },
        fetch = {
            fakeDataSource.queryJoke
        },
        saveFetchResult = { _ ->

        }
    ).flowOn(Dispatchers.Default)

    override fun getJokesFromQuery(searchQuery: String) = networkBoundResource(
        query = {
            fakeDataSource.queryJokes
        },
        fetch = {
            fakeDataSource.queryJokes
        },
        saveFetchResult = { _ ->

        }
    ).flowOn(Dispatchers.Default)

    override fun getJokesCategories(): Flow<Resource<List<Category>>> = networkBoundResource(
        query = {
            fakeDataSource.queryCategories
        },
        fetch = {
            fakeDataSource.queryCategories
        },
        saveFetchResult = { _ ->

        }
    ).flowOn(Dispatchers.Default)

    override fun getJokesByCategory(category: String) = networkBoundResource(
        query = {
            fakeDataSource.queryJokes
        },
        fetch = {
            fakeDataSource.queryJokes
        },
        saveFetchResult = { _ ->

        }
    ).flowOn(Dispatchers.Default)

}











