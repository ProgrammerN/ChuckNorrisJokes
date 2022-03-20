package com.dvt.chucknorrisjokes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dvt.chucknorrisjokes.repository.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the all the Jokes.
 */

@HiltViewModel
class JokesViewModel @Inject constructor(repositoryDefault: JokesRepository) : ViewModel() {

    val joke = repositoryDefault.getRandomJoke()
        .flowOn(Dispatchers.Default).asLiveData()

    //FlowMutable search query text
    val searchQuery = MutableStateFlow("")

    //FlowMutable joke category
    val searchCategory = MutableStateFlow("")

    //FlowMutable search results
    private val queryFlow = searchQuery.flatMapLatest {
        repositoryDefault.getJokesFromQuery(it)
    }.flowOn(Dispatchers.Default)

    //Random joke by search query as live data
    val queryJokeResults = queryFlow.asLiveData()

    //FlowMutable random joke by category
    private val randomJokesByCategoryFlow = searchCategory.flatMapLatest {
        repositoryDefault.getJokesByCategory(it)
    }.flowOn(Dispatchers.Default)

    //Random joke by category as live data
    val categoryJokesResult = randomJokesByCategoryFlow
        .flowOn(Dispatchers.Default)
        .asLiveData()

    // Events
    private val categoryEventChannel = Channel<CategoryEvent>()
    val categoryEvent = categoryEventChannel.receiveAsFlow()

    fun chooseCategoryClick() = viewModelScope.launch {
        categoryEventChannel.send(CategoryEvent.NavigateToCategoriesScreen)
    }

    sealed class CategoryEvent {
        object NavigateToCategoriesScreen : CategoryEvent()
    }

}