package de.palm.composestateevents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import kotlin.coroutines.CoroutineContext

/**
 * A side effect that gets executed when the given [event] changes to its triggered state.
 *
 * Other than the regular [EventEffect] this one will
 * consume the event before actually executing the action.
 *
 * @param event Pass the state event to be listened to from your view-state.
 * @param onConsumed In this callback you are advised to set the passed [event] to [StateEvent.Consumed] in your view-state.
 * @param action Callback that gets called in the composition's [CoroutineContext]. Perform the actual action this [event] leads to.
 */
@Composable
@NonRestartableComposable
fun NavigationEventEffect(
    event: StateEvent,
    onConsumed: () -> Unit,
    action: suspend () -> Unit
) {
    LaunchedEffect(key1 = event) {
        if (event is Triggered) {
            onConsumed()
            action()
        }
    }
}

/**
 * A side effect that gets executed when the given [event] changes to its triggered state.
 *
 * Other than the regular [EventEffect] this one will
 * consume the event before actually executing the action.
 *
 * @param event Pass the state event of type [T] to be listened to from your view-state.
 * @param onConsumed In this callback you are advised to set the passed [event] to an instance of [StateEventWithContentConsumed] in your view-state (see [consumed]).
 * @param action Callback that gets called in the composition's [CoroutineContext]. Perform the actual action this [event] leads to. The actual content of the [event] will be passed as an argument.
 */
@Composable
@NonRestartableComposable
fun <T> NavigationEventEffect(
    event: StateEventWithContent<T>,
    onConsumed: () -> Unit,
    action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = event) {
        if (event is StateEventWithContentTriggered<T>) {
            onConsumed()
            action(event.content)
        }
    }
}

/**
 * A side effect that gets executed when the given [event] changes to its triggered state.
 *
 * Other than the regular [EventEffect] this one will
 * consume the event before actually executing the action.
 *
 * @param event Pass the state event of type [T] to be listened to from your view-state.
 * @param action Callback that gets called in the composition's [CoroutineContext]. Perform the actual action this [event] leads to. The actual content of the [event] will be passed as an argument.
 */
@Composable
@NonRestartableComposable
fun NavigationEventEffect(
    event: AutoConsumeStateEvent,
    action: suspend () -> Unit
) {
    LaunchedEffect(key1 = event) {
        if (event is Triggered && !event.isConsumed()) {
            event.consume()
            action()
        }
    }
}

/**
 * A side effect that gets executed when the given [event] changes to its triggered state.
 *
 * Other than the regular [EventEffect] this one will
 * consume the event before actually executing the action.
 *
 * @param event Pass the state event of type [T] to be listened to from your view-state.
 * @param action Callback that gets called in the composition's [CoroutineContext]. Perform the actual action this [event] leads to. The actual content of the [event] will be passed as an argument.
 */
@Composable
@NonRestartableComposable
fun <T> NavigationEventEffect(
    event: AutoConsumeStateEventWithContent<T>,
    action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = event) {
        if (event is StateEventWithContentTriggered<T> && !event.isConsumed()) {
            event.consume()
            action(event.content)
        }
    }
}
