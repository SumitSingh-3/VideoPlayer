package com.example.datalib.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.datalib.utils.Either
import com.example.datalib.utils.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val job by lazy { SupervisorJob() }

    protected val ioScope by lazy { CoroutineScope(job + Dispatchers.IO) }

    protected inline fun <O : Either<Any, Failure>> handleUseCase(
        useCase: BaseUseCase<O>,
        crossinline response: (res: O) -> Unit,
    ) {
        ioScope.launch {
            response(
                useCase.process()
            )
        }
    }

    protected inline fun <I : Any, O: Either<Any, Failure>> handleUseCase(
        useCase: BaseUseCaseWithInput<I, O>,
        input: I,
        crossinline response: (res: O) -> Unit
    ) {
        ioScope.launch {
            response(useCase.process(input))
        }
    }

    protected inline fun <I: Any, reified O: Any> handleUseCase(
        useCase: BaseUseCaseWithInputUnWrapped<I, Flow<O>>,
        input: I,
        crossinline response: (res: O?) -> Unit
    ) {
        ioScope.launch {
            useCase.process(input).listen(this) {
                response(it)
            }
        }
    }

}



suspend inline fun <reified T> Flow<T>.listen(
    coScope: CoroutineScope,
    shareFlag: SharingStarted = SharingStarted.WhileSubscribed(),
    previousValuesCount: UInt = 1u,
    crossinline onResult: (res: T) -> Unit
) {
    catch { exception -> Log.e("Error : ", exception.message ?: "") }
    shareIn(
        coScope,
        shareFlag,
        previousValuesCount.toInt()
    ).collect { data ->
        onResult(data)
    }
}

suspend inline fun <reified T> Flow<T>.listen(
    coScope: CoroutineScope,
    shareFlag: SharingStarted = SharingStarted.WhileSubscribed(),
    previousValuesCount: UInt = 1u,
    crossinline onResult: (res: T) -> Unit,
    crossinline onFailure: (e: Throwable) -> Unit
) {
    catch { exception -> onFailure(exception) }
    shareIn(
        coScope,
        shareFlag,
        previousValuesCount.toInt()
    ).collect { data ->
        onResult(data)
    }
}