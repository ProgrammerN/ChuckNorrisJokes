package com.dvt.chucknorrisjokes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Query("SELECT * FROM jokes_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomJoke(): Flow<Joke>

    @Query("SELECT * FROM jokes_table WHERE value LIKE '%' || :searchQuery || '%'")
    fun getJokeFromQuery(searchQuery: String): Flow<List<Joke>>

    @Query("SELECT * FROM jokes_table WHERE value LIKE '%' || :category || '%'")
    fun getJokeByCategory(category: String): Flow<List<Joke>>

    @Query("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteJoke(joke: JokeFavorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokes(jokes: List<Joke>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("DELETE FROM jokes_table")
    suspend fun deleteAllJokes()

    @Query("DELETE FROM categories_table")
    suspend fun deleteAllCategories()
}