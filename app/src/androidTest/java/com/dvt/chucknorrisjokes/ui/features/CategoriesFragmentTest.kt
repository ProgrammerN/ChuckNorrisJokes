package com.dvt.chucknorrisjokes.ui.features

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.dvt.chucknorrisjokes.R
import com.dvt.chucknorrisjokes.adapters.CategoryAdapter
import com.dvt.chucknorrisjokes.launchFragmentInHiltContainer
import com.dvt.chucknorrisjokes.model.Category
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class CategoriesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        hiltRule.inject()
    }


    // Fleky Test, Please run app before performing test
    @Test
    fun pressItemInRecyclerViewAndNavigateBack() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<CategoriesFragment>() {
            Navigation.setViewNavController(requireView(), navController)
            val categoryAdapter = recyclerViewCategories.adapter as CategoryAdapter
            categoryAdapter.submitList(listOf(Category(1, "animal"), Category(2, "dev")))

        }

        onView(withId(R.id.recyclerViewCategories))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )

        verify(navController).popBackStack()
    }

}















