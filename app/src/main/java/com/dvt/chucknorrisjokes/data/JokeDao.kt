package com.dvt.chucknorrisjokes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Combine Data Access Object for the jokes table and categories table.
 */

@Dao
interface JokeDao {

    /**
     * Flow Observer for a random joke.
     *
     * @return random joke.
     */
    @Query("SELECT * FROM jokes_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomJoke(): Flow<Joke>

    /**
     * Flow Observer for a random joke.
     *
     * @param searchQuery the search text.
     * @return all jokes that match.
     */
    @Query("SELECT * FROM jokes_table WHERE value LIKE '%' || :searchQuery || '%'")
    fun getJokeFromQuery(searchQuery: String): Flow<List<Joke>>

    /**
     * Flow Observer list of jokes.
     *
     * @param category joke category.
     * @return all jokes that have the category.
     */
    @Query("SELECT * FROM jokes_table WHERE value LIKE '%' || :category || '%'")
    fun getJokeByCategory(category: String): Flow<List<Joke>>

    /**
     * Flow Observer list of categories.
     *
     * @return all categories.
     */
    @Query("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<Category>>

    /**
     * Insert a joke in the database. If the joke already exists, replace it.
     *
     * @param joke the joke to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    /**
     * Insert a favorite joke in the database. If the joke already exists, replace it.
     *
     * @param joke the joke to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteJoke(joke: JokeFavorite)

    /**
     * Insert a list of jokes in the database. If the list already exists, replace it.
     *
     * @param jokes the list of jokes to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokes(jokes: List<Joke>)

    /**
     * Insert a list of categories in the database. If the list already exists, replace it.
     *
     * @param categories the list of categories to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    /**
     * Delete all jokes.
     */
    @Query("DELETE FROM jokes_table")
    suspend fun deleteAllJokes()

    /**
     * Delete all categories.
     */
    @Query("DELETE FROM categories_table")
    suspend fun deleteAllCategories()
}