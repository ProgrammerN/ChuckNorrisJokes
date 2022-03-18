package com.dvt.chucknorrisjokes.features.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dvt.chucknorrisjokes.data.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(repository: JokesRepository) : ViewModel() {

    val categories = repository.getJokesCategories().asLiveData()

}