package com.example.comics.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeRunDispatcher(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> (T)
) = withContext(dispatcher) {
    return@withContext try {
        val result = block()
        Result.success(result)
    } catch (ex: Exception) {
        Result.failure(ex)
    }
}

