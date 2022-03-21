package com.dvt.chucknorrisjokes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dvt.chucknorrisjokes.repository.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the all the categories.
 */

@HiltViewModel
class CategoriesViewModel @Inject constructor(repository: JokesRepository) : ViewModel() {

    val categories = repository.getJokesCategories().asLiveData()

}