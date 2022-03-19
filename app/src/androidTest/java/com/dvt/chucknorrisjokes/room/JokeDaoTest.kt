package com.dvt.chucknorrisjokes.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.filters.SmallTest
import com.dvt.chucknorrisjokes.getOrAwaitValue
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.model.FavoriteJoke
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class JokeDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: JokesDatabase
    private lateinit var jokeDao: JokeDao


    @Before
    fun setup() {
        hiltRule.inject()
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

    @Test
    fun insertCategoryAndGetCategories() = runTest {

        val category = Category(id = 1, category = "animal")
        jokeDao.insertCategories(listOf(category))

        val categories = jokeDao.getCategories().asLiveData().getOrAwaitValue()

        assertThat(categories.size).isEqualTo(1)
        assertThat(categories[0].id).isEqualTo(category.id)
        assertThat(categories[0].category).isEqualTo(category.category)
    }

    @Test
    fun insertJokeInToFavoriteAndGetSameJoke() = runTest {

        val jokeItem = FavoriteJoke(
            listOf("animal"), "2020-01-05 13:42:19.576875",

            "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
            "xwjic1sws_yohsfefndiaw", "2020-01-05 13:42:19.576875",
            "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
            "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
        )
        jokeDao.insertFavoriteJoke(jokeItem)
        val allJokeItems = jokeDao.getFavoritedJokes().asLiveData().getOrAwaitValue()
        assertThat(allJokeItems).contains(jokeItem)
    }

}













