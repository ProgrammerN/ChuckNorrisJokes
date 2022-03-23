package com.dvt.chucknorrisjokes.repositories

import com.dvt.chucknorrisjokes.MainCoroutineRule
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of the in-memory repository with cache.
 */
@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {

    private val joke1 = Joke(
        listOf("animal"), "2020-01-05 13:42:19.576875",

        "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
        "xwjic1sws_yohsfefndias", "2020-01-05 13:42:19.576875",
        "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
        "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
    )
    private val joke2 = Joke(
        listOf("animal"), "2020-01-05 13:42:19.576875",

        "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
        "xwjic1sws_yohsfefndiaj", "2020-01-05 13:42:19.576875",
        "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
        "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
    )
    private val joke3 = Joke(
        listOf("animal"), "2020-01-05 13:42:19.576875",

        "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
        "xwjic1sws_yohsfefndiak", "2020-01-05 13:42:19.576875",
        "https://api.chucknorris.io/jokes/xwjic1sws_yohsfefndaiw",
        "Chuck Norris once kicked a horse in the chin. Its decendants are known today as Giraffes."
    )
    private val remoteJokes = listOf(joke1, joke2, joke3)

    private val category1 = Category(1, "animal")
    private val category2 = Category(2, "Description2")
    private val category3 = Category(3, "Description3")
    private val remoteCategories = listOf(category1, category2, category3).sortedBy { it.id }

    private lateinit var fakeDataSource: FakeDataSource

    // Class under test
    private lateinit var fakeDefaultJokesRepository: FakeDefaultJokesRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    @Before
    fun createRepository() {
        fakeDataSource = FakeDataSource(remoteJokes.toMutableList(), remoteCategories.toMutableList())
        // Get a reference to the class under test
        fakeDefaultJokesRepository = FakeDefaultJokesRepository(fakeDataSource)
    }

    @Test
    fun requestsAllJokesFromRemoteDataSource_CheckRandomJoke() = mainCoroutineRule.runBlockingTest {
        // When a random jokes is requested from the jokes repository
        var joke: Joke? = null
        testScope.launch {
            fakeDefaultJokesRepository.getRandomJoke().collect { jokeResults ->
                joke = jokeResults.data
            }
        }

        Truth.assertThat(remoteJokes).contains(joke)
    }

}

