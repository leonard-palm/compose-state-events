package de.palm.composestateeventslib.sample.ui.flower_detail

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

class FlowerDetailScreenViewModel : ViewModel() {

    private val _stateStream = MutableStateFlow(FlowerDetailScreenViewState())
    val stateStream = _stateStream.asStateFlow()

    private var state: FlowerDetailScreenViewState
        get() = _stateStream.value
        set(newState) {
            _stateStream.update { newState }
        }

    fun onClickedNavigateBack() {
        state = state.copy(navBackEvent = triggered)
    }

    fun onConsumeNavigateBackEvent() {
        state = state.copy(navBackEvent = triggered)
    }
}

data class FlowerDetailScreenViewState(
    val navBackEvent: StateEvent = consumed
)
