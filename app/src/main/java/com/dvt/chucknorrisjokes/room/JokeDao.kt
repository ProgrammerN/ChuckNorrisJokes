package com.dvt.chucknorrisjokes.room

import androidx.room.*
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.FavoriteJoke
import com.dvt.chucknorrisjokes.model.Joke
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
    @Query("SELECT * FROM jokes_table WHERE categories LIKE '%' || :category || '%'")
    fun getJokeByCategory(category: String): Flow<List<Joke>>

    /**
     * Flow Observer list of categories.
     *
     * @return all categories.
     */
    @Query("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<Category>>

    /**
     * Flow Observer list of categories.
     *
     * @return all categories.
     */
    @Query("SELECT * FROM favorites_table")
    fun getFavoriteJokes(): Flow<List<FavoriteJoke>>

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
    suspend fun insertFavoriteJoke(joke: FavoriteJoke)

    /**
     * Remove a favorite joke in the database
     *
     * @param joke the joke to be removed.
     */
    @Delete
    suspend fun removeFavoriteJoke(joke: FavoriteJoke)

    /**
     * Checks if jokes exists in favorites tables.
     *
     * @param id the joke to be inserted.
     */
    @Query("SELECT EXISTS(SELECT * FROM favorites_table WHERE id=(:id))")
    fun favoriteExists(id: String): Flow<Boolean>

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
     * Delete all favorite jokes.
     */
    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavoriteJokes()

    /**
     * Delete all categories.
     */
    @Query("DELETE FROM categories_table")
    suspend fun deleteAllCategories()
}