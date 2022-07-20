package com.currencies.domain

import kotlin.coroutines.cancellation.CancellationException

inline fun <T, R> T.tryCancellable(block: T.() -> R): Result<R> =
    try {
        Result.success(block())
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        Result.failure(e)
    }