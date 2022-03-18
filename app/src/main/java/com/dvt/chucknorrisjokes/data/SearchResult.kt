package com.dvt.chucknorrisjokes.data

data class SearchResult(
    val total: Int,
    val result: List<Joke>
)