package de.palm.composestateevents

/**
 *  This [StateEvent] can only have two primitive states.
 */
sealed interface StateEvent {
    /**
     *  The event is currently in it's triggered state
     */
    object Triggered : StateEvent

    /**
     *  The event is currently in it's consumed state
     */
    object Consumed : StateEvent
}

/**
 *  Shorter and more readable version of [StateEvent.Triggered]
 */
val triggered = StateEvent.Triggered

/**
 *  Shorter and more readable version of [StateEvent.Consumed]
 */
val consumed = StateEvent.Consumed
