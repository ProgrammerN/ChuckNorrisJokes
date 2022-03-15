package com.dvt.chucknorrisjokes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Query("SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1")
    fun getRandomJoke(): Flow<Joke>

    @Query("SELECT * FROM jokes WHERE value LIKE '%' || :searchQuery || '%'")
    fun getJokeFromQuery(searchQuery: String): Flow<List<Joke>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokes(jokes: List<Joke>)

    @Query("DELETE FROM jokes")
    suspend fun deleteAllJokes()
}