package com.dvt.chucknorrisjokes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvt.chucknorrisjokes.room.JokeDao
import com.dvt.chucknorrisjokes.room.JokesDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
class JokesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: JokesDatabase
    private lateinit var jokeDao: JokeDao

    @After
    fun teardown() {
        database.close()
    }

    private lateinit var viewModel: JokesViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        jokeDao = database.JokeDao()

        //viewModel = JokesViewModel(repository = JokesRepository(apiService =, db = database))
    }

}













