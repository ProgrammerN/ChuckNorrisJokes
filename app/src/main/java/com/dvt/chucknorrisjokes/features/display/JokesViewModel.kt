package com.dvt.chucknorrisjokes.features.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dvt.chucknorrisjokes.data.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the all the Jokes.
 */

@HiltViewModel
class JokesViewModel @Inject constructor(repository: JokesRepository) : ViewModel() {

    //Random joke as live data
    val joke = repository.getRandomJoke().asLiveData()

    //FlowMutable search query text
    val searchQuery = MutableStateFlow("")

    //FlowMutable joke category
    val searchCategory = MutableStateFlow("")

    //FlowMutable search results
    private val queryFlow = searchQuery.flatMapLatest {
        repository.getJokesFromQuery(it)
    }

    //Random joke by search query as live data
    val queryJokeResults = queryFlow.asLiveData()

    //FlowMutable random joke by category
    private val randomJokesByCategoryFlow = searchCategory.flatMapLatest {
        repository.getJokesByCategory(it)
    }

    //Random joke by category as live data
    val categoryJokesResult = randomJokesByCategoryFlow.asLiveData()

    // Demos
    private val categoryEventChannel = Channel<CategoryEvent>()
    val categoryEvent = categoryEventChannel.receiveAsFlow()

    fun chooseCategoryClick() = viewModelScope.launch {
        categoryEventChannel.send(CategoryEvent.NavigateToCategoriesScreen)
    }

    sealed class CategoryEvent {
        object NavigateToCategoriesScreen : CategoryEvent()
    }


}