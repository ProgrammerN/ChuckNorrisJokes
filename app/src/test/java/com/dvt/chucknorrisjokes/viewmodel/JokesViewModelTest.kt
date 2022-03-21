package com.dvt.chucknorrisjokes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dvt.chucknorrisjokes.MainCoroutineRule
import com.dvt.chucknorrisjokes.room.JokesDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class JokesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: JokesViewModel

    private lateinit var database: JokesDatabase

    private lateinit var ap: JokesDatabase

    @After
    fun teardown() {
        database.close()
    }

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JokesDatabase::class.java
        ).allowMainThreadQueries().build()

    }
}













