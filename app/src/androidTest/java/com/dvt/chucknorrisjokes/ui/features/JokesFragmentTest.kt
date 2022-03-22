package com.dvt.chucknorrisjokes.ui.features

import android.view.KeyEvent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.dvt.chucknorrisjokes.R
import com.dvt.chucknorrisjokes.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class JokesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickCategoriesMenuItem_navigateToCategoriesFragment() {

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<JokesFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.action_categories)).perform(click())

        Mockito.verify(navController).navigate(
            JokesFragmentDirections.actionJokesFragmentToChooseCategoryFragment()
        )
    }


}












