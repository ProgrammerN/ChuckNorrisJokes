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

        "Url 1",
        "id 01", "2020-01-05 13:42:19.576875",
        "Url 1",
        "horse in the chin. Its descendants"
    )
    private val joke2 = Joke(
        listOf("animal"), "2020-01-05 13:42:19.576875",

        "Url 2",
        "id 02", "2020-01-05 13:42:19.576875",
        "Url 2",
        "Its descendants are known today as Giraffes."
    )
    private val joke3 = Joke(
        listOf("dev"), "2020-01-05 13:42:19.576875",

        "Url 3",
        "id 03", "2020-01-05 13:42:19.576875",
        "Url 3",
        "Chuck Norris once kicked"
    )
    private val remoteJokes = listOf(joke1, joke2, joke3)

    private val category1 = Category(1, "animal")
    private val category2 = Category(2, "dev")
    private val category3 = Category(3, "career")
    private val remoteCategories = listOf(category1, category2, category3)

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
    fun requestsJokesBySearchQuery() = mainCoroutineRule.runBlockingTest {
        // When a random jokes is requested from the jokes repository
        var joke: List<Joke>
        testScope.launch {
            fakeDefaultJokesRepository.getJokesFromQuery("kicked").collect { jokeResults ->
                joke = jokeResults.data!!
            }
        }
        Truth.assertThat(remoteJokes).hasSize(3)
    }

    @Test
    fun requestsJokesByJokesByCategory() = mainCoroutineRule.runBlockingTest {
        // When a random jokes is requested from the jokes repository
        var joke: List<Joke>
        testScope.launch {
            fakeDefaultJokesRepository.getJokesByCategory("animal").collect { jokeResults ->
                joke = jokeResults.data!!
            }
        }
        Truth.assertThat(remoteJokes).hasSize(3)
    }


}

