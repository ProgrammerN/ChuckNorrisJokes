package com.dvt.chucknorrisjokes.ui.features

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvt.chucknorrisjokes.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JokesFragmentTest {

    @Test
    fun clickCategoriesMenuItem_navigateToCategoriesFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val titleScenario = launchFragmentInContainer<JokesFragment>()

        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(ViewMatchers.withId(R.id.action_categories)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.categoriesFragment)
    }
}












