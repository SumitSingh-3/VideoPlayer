package com.example.datalib.utils

inline fun <R : Any> processResponse(response: () -> R): Either<R, Failure> {
    return try {
        Either.Success(response())
    } catch (e: Exception) {
        Either.Error(Failure.Default(e))
    }
}