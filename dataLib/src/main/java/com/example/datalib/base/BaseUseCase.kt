package com.example.datalib.base

import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import kotlinx.coroutines.flow.Flow

interface BaseUseCase<out O : Either<Any, Failure>> {
    suspend fun process(): O
}

interface BaseUseCaseWithInput<in I : Any, out O : Either<Any, Failure>> {
    suspend fun process(input: I): O
}

interface BaseUseCaseWithInputUnWrapped<in I : Any, out O : Flow<Any>> {
    suspend fun process(input: I): O
}

interface BaseUseCaseUnwrappedWithInput<in I : Any, out O : Any> {
    suspend fun process(input: I): O
}