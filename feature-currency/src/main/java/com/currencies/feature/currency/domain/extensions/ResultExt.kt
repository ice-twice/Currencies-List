package com.currencies.feature.currency.domain.extensions

inline fun <T, R> Result<T>.foldSuccess(
    onSuccess: (T) -> R,
    onSuccessFailed: (Exception) -> Exception = { it }
): Result<R> {
    return fold(
        onSuccess = {
            try {
                Result.success(onSuccess(it))
            } catch (e: Exception) {
                Result.failure(onSuccessFailed(e))
            }
        },
        onFailure = {
            @Suppress("UNCHECKED_CAST")
            this as Result<R> // it is safe because it is already failed and the type does not matter
        }
    )
}