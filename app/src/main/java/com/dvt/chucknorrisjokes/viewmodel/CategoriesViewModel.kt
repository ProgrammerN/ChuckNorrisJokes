package com.dvt.chucknorrisjokes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dvt.chucknorrisjokes.repository.DefaultJokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * ViewModel for the all the categories.
 */

@HiltViewModel
class CategoriesViewModel @Inject constructor(repositoryDefault: DefaultJokesRepository) : ViewModel() {

    val categories = repositoryDefault.getJokesCategories()
        .flowOn(Dispatchers.Default).asLiveData()

}