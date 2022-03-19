package com.dvt.chucknorrisjokes.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dvt.chucknorrisjokes.getOrAwaitValue
import com.dvt.chucknorrisjokes.model.Joke
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class JokeDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: JokesDatabase
    private lateinit var jokeDao: JokeDao


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JokesDatabase::class.java
        ).allowMainThreadQueries().build()
        jokeDao = database.JokeDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun deleteAndInsertSingleJokeItemAndReadItAsRandom() = runTest {
        val jokeItem = Joke(
            listOf("animal"), "2020-01-05 13:42:19.576875",
            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "xwjic1sws_yohsfefndaiw", "2020-01-05 13:42:19.576875",
            "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
            "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
        )
        jokeDao.deleteAllJokes()
        jokeDao.insertJoke(jokeItem)

        val allJokeItems = jokeDao.getRandomJoke().asLiveData().getOrAwaitValue()
        assertThat(allJokeItems).isEqualTo(jokeItem)
    }

    @Test
    fun insertAndAccess_A_JokeByItsCategory() = runTest {
        val jokeItem = Joke(
            listOf("animal"), "2020-01-05 13:42:19.576875",

            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "xwjic1sws_yohsfefndiaw", "2020-01-05 13:42:19.576875",
            "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
            "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
        )

        jokeDao.insertJoke(jokeItem)


        val allJokeItems = jokeDao.getJokeByCategory("animal").asLiveData().getOrAwaitValue()

        assertThat(allJokeItems).contains(jokeItem)
    }

    @Test
    fun deleteAllJokesReturnEmptyList() = runTest {
        val jokeItem1 = Joke(
            listOf("animal"), "2020-01-05 13:42:19.576875",

            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "xwjic1sws_yohsfefndias", "2020-01-05 13:42:19.576875",
            "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
            "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
        )
        val jokeItem2 = Joke(
            listOf("animal"), "2020-01-05 13:42:19.576875",

            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "xwjic1sws_yohsfefndiaj", "2020-01-05 13:42:19.576875",
            "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
            "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
        )
        val jokeItem3 = Joke(
            listOf("animal"), "2020-01-05 13:42:19.576875",

            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "xwjic1sws_yohsfefndiak", "2020-01-05 13:42:19.576875",
            "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
            "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
        )

        jokeDao.insertJoke(jokeItem1)
        jokeDao.insertJoke(jokeItem2)
        jokeDao.insertJoke(jokeItem3)
        jokeDao.deleteAllJokes()

        val allJokeItems = jokeDao.getJokeByCategory("animal").asLiveData().getOrAwaitValue()
        assertThat(allJokeItems).isEmpty()
    }

}













