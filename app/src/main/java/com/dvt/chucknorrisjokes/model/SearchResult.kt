package com.dvt.chucknorrisjokes.model

/**
 * Data class for holding api response.
 *
 * @param total total results found
 * @param result list of jokes
 */
data class SearchResult(
    val total: Int,
    val result: List<Joke>
)