package com.dvt.chucknorrisjokes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Joke::class, Category::class, JokeFavorite::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun JokeDao(): JokeDao
}