package com.dvt.chucknorrisjokes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvt.chucknorrisjokes.MainCoroutineRule
import com.dvt.chucknorrisjokes.model.Category
import com.dvt.chucknorrisjokes.model.Joke
import com.dvt.chucknorrisjokes.repositories.FakeDataSource
import com.dvt.chucknorrisjokes.repositories.FakeDefaultJokesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class JokesViewModelTest {

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
    private val remoteTasks = listOf(joke1, joke2, joke3)

    private val category1 = Category(1, "animal")
    private val category2 = Category(2, "Description2")
    private val category3 = Category(3, "Description3")
    private val remoteCategories = listOf(category1, category2, category3).sortedBy { it.id }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: JokesViewModel

    private lateinit var fakeDataSource: FakeDataSource

    private lateinit var fakeDefaultJokesRepository: FakeDefaultJokesRepository

    @Before
    fun createRepository() {
        fakeDataSource = FakeDataSource(remoteTasks.toMutableList(), remoteCategories.toMutableList())
        fakeDefaultJokesRepository = FakeDefaultJokesRepository(fakeDataSource)
    }

    @Before
    fun setup() {
        viewModel = JokesViewModel(fakeDefaultJokesRepository)
    }


}













