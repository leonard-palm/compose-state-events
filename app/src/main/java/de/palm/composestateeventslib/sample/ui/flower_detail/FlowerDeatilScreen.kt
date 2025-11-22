package de.palm.composestateeventslib.sample.ui.flower_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.palm.composestateevents.NavigationEventEffect
import de.palm.composestateeventslib.sample.extensions.collectAsStateLifecycleAware

@Composable
fun FlowerDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val viewModel: FlowerDetailScreenViewModel = viewModel()
    val screenState: FlowerDetailScreenViewState by viewModel.stateStream.collectAsStateLifecycleAware()

    NavigationEventEffect(
        event = screenState.navBackEvent,
        onConsumed = {
            viewModel.onConsumeNavigateBackEvent()
        }
    ) {
        navController.popBackStack()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                viewModel.onClickedNavigateBack()
            }
        ) {
            Text(text = "Navigate Back")
        }
    }
}
