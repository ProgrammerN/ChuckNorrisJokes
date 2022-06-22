package com.dvt.chucknorrisjokes.util

import kotlinx.coroutines.flow.*

/**
 * Concrete implementation of network bound response.
 * @param <ResultType, RequestType>
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline fetchFromLocal: () -> Flow<ResultType>,
    crossinline fetchFromNetwork: suspend () -> RequestType,
    crossinline offlineCacheResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = fetchFromLocal().first()
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            offlineCacheResult(fetchFromNetwork())
            fetchFromLocal().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            fetchFromLocal().map { Resource.Error(throwable, it) }
        }
    } else {
        fetchFromLocal().map { Resource.Success(it) }
    }
    emitAll(flow)
}