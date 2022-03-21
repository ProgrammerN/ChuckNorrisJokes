package com.dvt.chucknorrisjokes.repository

import androidx.room.withTransaction
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.FavoriteJoke
import com.dvt.chucknorrisjokes.retrofit.JokesApiService
import com.dvt.chucknorrisjokes.room.JokesDatabase
import com.dvt.chucknorrisjokes.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Concrete implementation of a JokesDatabase & JokesApiService.
 * Gets available jokes either from the cache(Local Database) or From The Network
 * Save results from remote server to local room database fro later use as cache
 * In the event there is no network result is fetched from local cache
 */
open class DefaultJokesRepository @Inject constructor(private val apiService: JokesApiService, private val db: JokesDatabase) : JokesRepository {

    private val jokesJokeDao = db.JokeDao()

    override fun getRandomJoke() = networkBoundResource(
        query = {
            jokesJokeDao.getRandomJoke()
        },
        fetch = {
            apiService.getRandomJoke()
        },
        saveFetchResult = { jokes ->
            db.withTransaction {
                jokesJokeDao.deleteAllJokes()
                jokesJokeDao.insertJoke(jokes)
            }
        }
    )

    override fun getJokesFromQuery(searchQuery: String) = networkBoundResource(
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

    override fun getJokesCategories() = networkBoundResource(
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

    override fun getJokesByCategory(category: String) = networkBoundResource(
        query = {
            jokesJokeDao.getJokeByCategory(category)
        },
        fetch = {
            apiService.getJokeByCategory(category)
        },
        saveFetchResult = { joke ->
            db.withTransaction {
                jokesJokeDao.insertJoke(joke)
            }
        }
    )

    override suspend fun favoriteJoke(joke: FavoriteJoke) {
        jokesJokeDao.insertFavoriteJoke(joke)
    }

    override suspend fun removeFavoriteJoke(joke: FavoriteJoke) {
        jokesJokeDao.removeFavoriteJoke(joke)
    }

    override suspend fun deleteAllFavorites() {
        jokesJokeDao.deleteAllFavoriteJokes()
    }

    override suspend fun getFavorites(): Flow<List<FavoriteJoke>> {
        return jokesJokeDao.getFavoriteJokes()
    }

    override suspend fun favoriteExists(id: String): Flow<Boolean> {
        return jokesJokeDao.favoriteExists(id)
    }

}