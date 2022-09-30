package de.palm.composestateevents

typealias StateEvent = Any

data class StateEventWithContent<T>(val content: T)

val triggered: StateEvent = StateEvent()
fun <T> triggered(content: T): StateEventWithContent<T> = StateEventWithContent(content)

val consumed: Nothing? = null
