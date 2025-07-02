package de.palm.composestateevents

import androidx.compose.runtime.Immutable

/**
 *  This [AutoConsumeStateEventWithContent] can only have two primitive states.
 *  And, the event will automatically be consumed from effect.
 *  Additionally, the triggered state holds a value of type [T].
 */

@Immutable
sealed interface AutoConsumeStateEventWithContent<out T> {
    fun isConsumed(): Boolean
    fun consume()
}

/**
 *  This [StateEventWithContent] can have exactly 2 states like the [StateEvent] but the triggered state holds a value of type [T].
 */
@Immutable
sealed interface StateEventWithContent<out T>

/**
 * The event in its triggered state holding a value of [T]. See [triggered] to create an instance of this.
 * @param content A value that is needed on the event consumer side.
 */
@Immutable
data class StateEventWithContentTriggered<T>(val content: T) : StateEventWithContent<T>, AutoConsumeStateEventWithContent<T> {
    private var consumed = false

    override fun isConsumed(): Boolean = consumed
    override fun consume() { consumed = true }

    override fun toString(): String = "triggered($content)"
}

/**
 * The event in its consumed state not holding any value. See [consumed] to create an instance of this.
 */
@Immutable
object StateEventWithContentConsumed : StateEventWithContent<Nothing>, AutoConsumeStateEventWithContent<Nothing> {
    override fun isConsumed(): Boolean = true
    override fun consume() {}

    override fun toString(): String = "consumed"
}

/**
 * A shorter and more readable way to create an [StateEventWithContent] in its triggered state holding a value of [T].
 * @param content A value that is needed on the event consumer side.
 */
fun <T> triggered(content: T) = StateEventWithContentTriggered(content)

/**
 * A shorter and more readable way to create an [StateEventWithContent] in its consumed state.
 */
fun consumed() = StateEventWithContentConsumed
