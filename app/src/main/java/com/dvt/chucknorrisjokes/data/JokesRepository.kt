package com.dvt.chucknorrisjokes.data

import androidx.room.withTransaction
import com.dvt.chucknorrisjokes.api.JokesApiService
import com.dvt.chucknorrisjokes.util.networkBoundResource
import javax.inject.Inject

class JokesRepository @Inject constructor(private val apiService: JokesApiService, private val db: JokesDatabase) {

    private val jokesJokeDao = db.JokeDao()

    fun getRandomJoke() = networkBoundResource(
        query = {
            jokesJokeDao.getRandomJoke()
        },
        fetch = {
            apiService.getRandomJoke()
        },
        saveFetchResult = { jokes ->
            db.withTransaction {
                jokesJokeDao.insertJoke(jokes)
            }
        }
    )

    fun getJokesFromQuery(searchQuery: String) = networkBoundResource(
        query = {
            jokesJokeDao.getJokeFromQuery(searchQuery)
        },
        fetch = {
            apiService.getJokesByQuery(searchQuery)
        },
        saveFetchResult = { jokes ->
            db.withTransaction {
                jokesJokeDao.deleteAllJokes()
                jokesJokeDao.insertJokes(jokes.result)
            }
        }
    )

    fun getJokesCategories() = networkBoundResource(
        query = {
            jokesJokeDao.getCategories()
        },
        fetch = {
            apiService.getJokesCategories()
        },
        saveFetchResult = { categories ->
            db.withTransaction {
                jokesJokeDao.deleteAllCategories()
                jokesJokeDao.insertCategories(categories.map { category ->
                    Category(category = category)
                })
            }
        }
    )

    fun getJokesByCategory(category: String) = networkBoundResource(
        query = {
            jokesJokeDao.getJokeByCategory(category)
        },
        fetch = {
            apiService.getJokeByCategory(category)
        },
        saveFetchResult = { joke ->
            db.withTransaction {
                jokesJokeDao.insertJokes(joke.result)
            }
        }
    )
}