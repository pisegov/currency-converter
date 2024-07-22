package com.myaxa.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

inline fun <T> Flow<Result<T>>.onSuccess(crossinline action: (value: T) -> Unit): Flow<Result<T>> {
    return onEach {
        it.onSuccess { value -> action(value) }
    }
}

inline fun <T> Flow<Result<T>>.onFailure(crossinline action: (exception: Throwable) -> Unit): Flow<Result<T>> {
    return onEach {
        it.onFailure { e -> action(e) }
    }
}

inline fun <T, R> Flow<Result<T>>.mapResult(crossinline transform: suspend (value: T) -> R): Flow<Result<R>> {
    return map { result -> result.map { value -> transform(value) } }
}
