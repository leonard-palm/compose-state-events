package de.palm.composestateevents

import androidx.compose.runtime.Immutable

/**
 *  This [StateEvent] can only have two primitive states.
 */
@Immutable
sealed interface StateEvent {
    /**
     *  The event is currently in its triggered state
     */
    @Immutable
    object Triggered : StateEvent {
        override fun toString(): String = "triggered"
    }

    /**
     *  The event is currently in its consumed state
     */
    @Immutable
    object Consumed : StateEvent {
        override fun toString(): String = "consumed"
    }
}

/**
 *  Shorter and more readable version of [StateEvent.Triggered]
 */
val triggered = StateEvent.Triggered

/**
 *  Shorter and more readable version of [StateEvent.Consumed]
 */
val consumed = StateEvent.Consumed
