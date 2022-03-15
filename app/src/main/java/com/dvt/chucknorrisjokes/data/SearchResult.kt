package com.dvt.chucknorrisjokes.data

data class SearchResult(
    val total: Int? = null,
    val result: List<Joke> = arrayListOf()
)