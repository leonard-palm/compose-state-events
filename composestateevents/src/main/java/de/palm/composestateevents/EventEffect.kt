package de.palm.composestateevents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

@Composable
fun EventEffect(event: StateEvent?, onConsumed: () -> Unit, block: suspend CoroutineScope.() -> Unit) {
    LaunchedEffect(key1 = event) {
        if (event != null) {
            block()
            onConsumed()
        }
    }
}

@Composable
fun <T> EventEffect(event: StateEventWithContent<T>?, onConsumed: () -> Unit, block: suspend CoroutineScope.(T) -> Unit) {
    LaunchedEffect(key1 = event) {
        if (event != null) {
            block(event.content)
            onConsumed()
        }
    }
}
