package com.dvt.chucknorrisjokes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvt.chucknorrisjokes.MainCoroutineRule
import com.dvt.chucknorrisjokes.getOrAwaitValueTest
import com.dvt.chucknorrisjokes.repositories.FakeDefaultJokesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class JokesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: JokesViewModel

    @Before
    fun setup() {

        viewModel = JokesViewModel(FakeDefaultJokesRepository())
    }

    /*@Test
    fun `empty search string, returns zero results`() {

        viewModel.searchCategory.value = ""

        val value = viewModel.queryJokeResults.getOrAwaitValueTest()

        assertThat(value.data).isEmpty()
    }*/

}













