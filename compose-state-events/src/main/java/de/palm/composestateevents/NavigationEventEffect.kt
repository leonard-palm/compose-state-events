package de.palm.composestateevents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Other than the regular [EventEffect] this one will
 * consume the event before actually executing the action.
 */
@Composable
fun <T> NavigationEventEffect(
    event: StateEventWithContent<T>,
    onConsumed: () -> Unit,
    action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = event, key2 = onConsumed) {
        if (event is StateEventWithContentTriggered<T>) {
            onConsumed()
            action(event.content)
        }
    }
}

/**
 * Other than the regular [EventEffect] this one will
 * consume the event before actually executing the action.
 */
@Composable
fun NavigationEventEffect(
    event: StateEvent,
    onConsumed: () -> Unit,
    action: suspend () -> Unit
) {
    LaunchedEffect(key1 = event, key2 = onConsumed) {
        if (event is StateEvent.Triggered) {
            onConsumed()
            action()
        }
    }
}
