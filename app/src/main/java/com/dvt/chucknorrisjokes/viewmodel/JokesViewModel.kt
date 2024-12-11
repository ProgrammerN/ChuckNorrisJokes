package com.dvt.chucknorrisjokes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dvt.chucknorrisjokes.model.FavoriteJoke
import com.dvt.chucknorrisjokes.repository.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for the all the Jokes.
 */

@HiltViewModel
class JokesViewModel @Inject constructor(private val jokesRepository: JokesRepository) : ViewModel() {

    val joke = jokesRepository.getRandomJoke()
        .flowOn(Dispatchers.IO).asLiveData()

    //FlowMutable search query text
    val searchQuery = MutableStateFlow("")

    //FlowMutable joke category
    val searchCategory = MutableStateFlow("")

    //FlowMutable search results
    private val queryFlow = searchQuery.flatMapLatest {
        jokesRepository.getJokesFromQuery(it)
    }.flowOn(Dispatchers.IO)

    //Random joke by search query as live data
    val queryJokeResults = queryFlow.asLiveData()

    //FlowMutable random joke by category
    private val randomJokesByCategoryFlow = searchCategory.flatMapLatest {
        jokesRepository.getJokesByCategory(it)
    }.flowOn(Dispatchers.IO)

    //Random joke by category as live data
    val categoryJokesResult = randomJokesByCategoryFlow
        .flowOn(Dispatchers.IO)
        .asLiveData()

    // Category Events
    private val jokesEventChannel = Channel<JokesEvent>()
    val jokesEvent = jokesEventChannel.receiveAsFlow()


    fun chooseCategoryClick() = viewModelScope.launch {
        jokesEventChannel.send(JokesEvent.NavigateToCategoriesScreen)
    }

    fun goToFavoriteClick() = viewModelScope.launch {
        jokesEventChannel.send(JokesEvent.NavigateToFavoriteJokesScreen)
    }

    fun favoriteJoke(joke: FavoriteJoke) = viewModelScope.launch(Dispatchers.IO) {
        jokesRepository.favoriteJoke(joke)
    }

    fun removeFavoriteJoke(joke: FavoriteJoke) = viewModelScope.launch(Dispatchers.IO) {
        jokesRepository.removeFavoriteJoke(joke)
    }

    fun deleteAllFavorites() = viewModelScope.launch(Dispatchers.IO) {
        jokesRepository.deleteAllFavorites()
    }

    sealed class JokesEvent {
        object NavigateToCategoriesScreen : JokesEvent()
        object NavigateToFavoriteJokesScreen : JokesEvent()
    }


    private val _favoriteJokes = MutableLiveData<List<FavoriteJoke>>()

    fun favoriteJokes(): MutableLiveData<List<FavoriteJoke>> {
        viewModelScope.launch(Dispatchers.IO) {
            jokesRepository.getFavorites().collect { jokeResults ->
                _favoriteJokes.postValue(jokeResults)
            }
        }

        return _favoriteJokes
    }

    val jokeId = MutableStateFlow("")

    private val isJokeByIdExist = jokeId.flatMapLatest {
        jokesRepository.favoriteExists(it)
    }.flowOn(Dispatchers.IO)

    val isFavorite = isJokeByIdExist.flowOn(Dispatchers.IO).asLiveData()

}