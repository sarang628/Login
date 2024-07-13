package com.sarang.torang.compose.signinsignup

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> Flow<T>.getOrAwaitValue(
    time: Long = 5,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    return runBlocking {
        withTimeout(timeUnit.toMillis(time)) {
            this@getOrAwaitValue.first()
        }
    } ?: throw TimeoutException("Flow value was never set.")
}