package com.dvt.chucknorrisjokes.data

/**
 * Data class for a api results.
 *
 * @param total       total results found
 * @param result list of jokes
 */
data class SearchResult(
    val total: Int,
    val result: List<Joke>
)