package com.dvt.chucknorrisjokes.ui.features

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvt.chucknorrisjokes.launchFragmentInHiltContainer
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class CategoriesFragmentTest {

    @Test
    fun pressBackButton_popBackStack() {

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<CategoriesFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBack()
        verify(navController).popBackStack()

    }
}















