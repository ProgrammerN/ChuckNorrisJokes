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
import javax.inject.Inject

/**
 * ViewModel for the all the Jokes.
 */

@HiltViewModel
class JokesViewModel @Inject constructor(private val repositoryDefault: JokesRepository) : ViewModel() {

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

    // Category Events
    private val jokesEventChannel = Channel<JokesEvent>()
    val jokesEvent = jokesEventChannel.receiveAsFlow()


    fun chooseCategoryClick() = viewModelScope.launch {
        jokesEventChannel.send(JokesEvent.NavigateToCategoriesScreen)
    }

    fun goToFavoriteClick() = viewModelScope.launch {
        jokesEventChannel.send(JokesEvent.NavigateToFavoriteJokesScreen)
    }

    fun favoriteJoke(joke: FavoriteJoke) = viewModelScope.launch {
        repositoryDefault.favoriteJoke(joke)
    }

    fun removeFavoriteJoke(joke: FavoriteJoke) = viewModelScope.launch {
        repositoryDefault.removeFavoriteJoke(joke)
    }

    fun deleteAllFavorites() = viewModelScope.launch {
        repositoryDefault.deleteAllFavorites()
    }

    sealed class JokesEvent {
        object NavigateToCategoriesScreen : JokesEvent()
        object NavigateToFavoriteJokesScreen : JokesEvent()
    }


    private val _favoriteJokes = MutableLiveData<List<FavoriteJoke>>()

    private val _isFavorite = MutableLiveData<Boolean>()

    fun favoriteJokes(): MutableLiveData<List<FavoriteJoke>> {
        return _favoriteJokes
    }

    val id = MutableStateFlow("")

    private val isJokeByIdExist = id.flatMapLatest {
        repositoryDefault.favoriteExists(it)
    }.flowOn(Dispatchers.Default)


    val isFavorite = isJokeByIdExist.flowOn(Dispatchers.Default).asLiveData()

    init {
        viewModelScope.launch {
            repositoryDefault.getFavorites().collect { jokeResults ->
                _favoriteJokes.value = jokeResults
            }

        }
    }

}