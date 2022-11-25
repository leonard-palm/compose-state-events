package de.palm.composestateeventslib.sample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import kotlinx.coroutines.delay
import de.palm.composestateeventslib.R
import de.palm.composestateevents.triggered
import de.palm.composestateeventslib.sample.domain.Flower
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class FlowerScreenViewModel : ViewModel() {

    private val _stateStream = MutableStateFlow(FlowerScreenViewState())
    val stateStream = _stateStream.asStateFlow()

    private var state: FlowerScreenViewState
        get() = _stateStream.value
        set(newState) {
            _stateStream.update { newState }
        }

    fun loadFlowersWithSuccess() {
        if (state.isLoading) return
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(2_000L)
            state = state.copy(
                isLoading = false,
                flowers = mockedFlowerList,
                downloadSucceededEvent = triggered
            )
        }
    }

    fun loadFlowersWithFailure() {
        if (state.isLoading) return
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(1_000L)
            state = state.copy(
                isLoading = false,
                downloadFailedEvent = triggered(R.string.flowers_could_not_load)
            )
        }
    }

    fun onConsumedDownloadSucceededEvent() {
        state = state.copy(downloadSucceededEvent = consumed)
    }

    fun onConsumedDownloadFailedEvent() {
        state = state.copy(downloadFailedEvent = consumed())
    }
}

data class FlowerScreenViewState(
    val isLoading: Boolean = false,
    val flowers: List<Flower> = emptyList(),
    val downloadSucceededEvent: StateEvent = consumed,
    val downloadFailedEvent: StateEventWithContent<Int> = consumed()
)

private val mockedFlowerList = listOf(
    Flower(uid = UUID.randomUUID(), name = "Flower1", color = "red"),
    Flower(uid = UUID.randomUUID(), name = "Flower2", color = "blue"),
    Flower(uid = UUID.randomUUID(), name = "Flower3", color = "green")
)
