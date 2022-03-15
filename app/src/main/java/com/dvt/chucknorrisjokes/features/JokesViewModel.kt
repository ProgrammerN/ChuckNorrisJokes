package com.dvt.chucknorrisjokes.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dvt.chucknorrisjokes.data.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(repository: JokesRepository) : ViewModel() {

    val jokes = repository.getRandomJoke().asLiveData()

    val searchQuery = MutableStateFlow("hello")

    private val tasksFlow = searchQuery.flatMapLatest {
        repository.getJokesFromQuery(it)
    }

    val queryJokeResults = tasksFlow.asLiveData()
}