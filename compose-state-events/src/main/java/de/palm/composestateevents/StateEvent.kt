package de.palm.composestateevents

import androidx.compose.runtime.Immutable

/**
 *  This [AutoConsumeStateEvent] can only have two primitive states.
 *  And, the event will automatically be consumed from effect.
 */

@Immutable
sealed interface AutoConsumeStateEvent {
    fun isConsumed(): Boolean
    fun consume()
}

/**
 *  This [StateEvent] can only have two primitive states.
 */
@Immutable
sealed interface StateEvent

/**
 *  The event is currently in its triggered state
 */
@Immutable
class Triggered : StateEvent, AutoConsumeStateEvent {
    private var consumed = false

    override fun isConsumed() = consumed
    override fun consume() { consumed = true }

    override fun toString(): String = "triggered"
}

/**
 *  The event is currently in its consumed state
 */
@Immutable
object Consumed : StateEvent, AutoConsumeStateEvent {

    override fun isConsumed() = true
    override fun consume() { }

    override fun toString(): String = "consumed"
}

/**
 *  Shorter and more readable version of [StateEvent.Triggered]
 */
val triggered: Triggered get() { return Triggered() }

/**
 *  Shorter and more readable version of [StateEvent.Consumed]
 */
val consumed: Consumed = Consumed
