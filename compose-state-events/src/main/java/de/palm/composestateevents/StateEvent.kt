package de.palm.composestateevents

/**
 *  This [StateEvent] can only have two primitive states.
 */
sealed interface StateEvent {
    /**
     *  The event is currently in its triggered state
     */
    object Triggered : StateEvent {
        override fun toString(): String = "triggered"
    }

    /**
     *  The event is currently in its consumed state
     */
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
