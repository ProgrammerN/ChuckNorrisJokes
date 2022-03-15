package com.dvt.chucknorrisjokes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Joke::class], version = 1, exportSchema = false)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun JokeDao(): JokeDao
}