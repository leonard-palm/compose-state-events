package de.palm.composestateevents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 *  A side effect that gets executed when the given [event] changes to it's triggered state.
 * 
 *  @param event Pass the state event to be listened to from your view-state.
 *  @param onConsumed In this callback you are advised to set the passed [event] to [StateEvent.Consumed] in your view-state.
 *  @param block Callback that gets called in the composition's [CoroutineContext]. Perform the actual action this [event] leads to.
 */
@Composable
fun EventEffect(event: StateEvent, onConsumed: () -> Unit, block: suspend CoroutineScope.() -> Unit) {
    LaunchedEffect(key1 = event, key2 = onConsumed) {
        if (event is StateEvent.Triggered) {
            block()
            onConsumed()
        }
    }
}

/**
 *  A side effect that gets executed when the given [event] changes to it's triggered state.
 *
 *  @param event Pass the state event of type [T] to be listened to from your view-state.
 *  @param onConsumed In this callback you are advised to set the passed [event] to an instance of [StateEventWithContentConsumed] in your view-state (see [consumed]).
 *  @param block Callback that gets called in the composition's [CoroutineContext]. Perform the actual action this [event] leads to. The actual content of the [event] will be passed as an argument.
 */
@Composable
fun <T> EventEffect(event: StateEventWithContent<T>, onConsumed: () -> Unit, block: suspend CoroutineScope.(T) -> Unit) {
    LaunchedEffect(key1 = event, key2 = onConsumed) {
        if (event is StateEventWithContentTriggered<T>) {
            block(event.content)
            onConsumed()
        }
    }
}
