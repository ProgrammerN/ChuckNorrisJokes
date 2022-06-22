package com.dvt.chucknorrisjokes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dvt.chucknorrisjokes.repository.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * ViewModel for the all the categories.
 */

@HiltViewModel
class CategoriesViewModel @Inject constructor(jokesRepository: JokesRepository) : ViewModel() {

    val categories = jokesRepository.getJokesCategories().flowOn(Dispatchers.IO).asLiveData()

}