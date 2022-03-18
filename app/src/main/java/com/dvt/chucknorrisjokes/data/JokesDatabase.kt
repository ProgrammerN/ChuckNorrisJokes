package com.dvt.chucknorrisjokes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dvt.chucknorrisjokes.app.Constants

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