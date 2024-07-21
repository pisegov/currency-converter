package com.myaxa.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile

inline fun <T> Flow<Result<T>>.onFailure(crossinline action: (exception: Throwable) -> Unit): Flow<Result<T>> {
    return onEach {
        it.onFailure { e -> action(e) }
    }
}

fun <T> Flow<Result<T>>.takeSuccess(): Flow<T> {
    return takeWhile { it.isSuccess }.map { it.getOrThrow() }
}
