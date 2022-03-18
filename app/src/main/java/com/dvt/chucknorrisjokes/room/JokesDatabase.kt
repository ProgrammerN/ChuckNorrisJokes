package com.dvt.chucknorrisjokes.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dvt.chucknorrisjokes.app.Constants
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.model.JokeFavorite

/**
 * The Room Database that contains the Jokes table, Categories table & JokeFavorite table.
 *
 * Note that exportSchema should be true in production databases.
 */

@Database(
    entities = [Joke::class, Category::class, JokeFavorite::class],
    version = Constants.DATABASE_VERSION,
    exportSchema = Constants.DATABASE_EXPORT_SCHEMA
)
@TypeConverters(Converters::class)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun JokeDao(): JokeDao
}