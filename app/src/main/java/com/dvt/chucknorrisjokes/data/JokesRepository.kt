package com.dvt.chucknorrisjokes.data

import androidx.room.withTransaction
import com.dvt.chucknorrisjokes.api.JokesApiService
import com.dvt.chucknorrisjokes.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class JokesRepository @Inject constructor(private val apiService: JokesApiService, private val db: JokesDatabase) {

    private val jokesJokeDao = db.JokeDao()

    fun getRandomJoke() = networkBoundResource(
        query = {
            jokesJokeDao.getRandomJoke()
        },
        fetch = {
            delay(2000)
            apiService.getRandomJoke()
        },
        saveFetchResult = { jokes ->
            db.withTransaction {
                jokesJokeDao.deleteAllJokes()
                jokesJokeDao.insertJoke(jokes)
            }
        }
    )

    fun getJokesFromQuery(searchQuery: String) = networkBoundResource(
        query = {
            jokesJokeDao.getJokeFromQuery(searchQuery)
        },
        fetch = {
            delay(2000)
            apiService.getJokesByQuery(searchQuery)
        },
        saveFetchResult = { jokes ->
            db.withTransaction {
                jokesJokeDao.deleteAllJokes()
                jokesJokeDao.insertJokes(jokes.first().result)
            }
        }
    )
}